import { useState, useEffect, useCallback, useMemo, useRef } from "react";
import {
  Box, Button, TextField, Typography, Avatar, IconButton, Card, CardContent,
  MenuItem, Select, FormControl,
} from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import {
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer,
} from "recharts";

const formatTimestamp = (timestamp) => {
  const date = new Date(timestamp);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${month}/${day} ${hours}:${minutes}`;
};

const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    return (
      <Box sx={{ p: 1, bgcolor: 'rgba(255, 255, 255, 0.9)', border: '1px solid #ccc', borderRadius: 1, boxShadow: 3 }}>
        <Typography variant="body2" fontWeight="bold" color="#9146FF">Time: {formatTimestamp(label)}</Typography>
        <Typography variant="body2">Hits: {payload[0].value.toLocaleString()}</Typography>
      </Box>
    );
  }
  return null;
};

const TIME_CONFIG = {
  MINUTELY: [
    { label: "1H", val: 1 }, { label: "3H", val: 3 }, { label: "6H", val: 6 }, { label: "12H", val: 12 }, { label: "24H", val: 24 }
  ],
  HOURLY: [
    { label: "1H", val: 1 }, { label: "3H", val: 3 }, { label: "6H", val: 6 }, { label: "12H", val: 12 }, { label: "24H", val: 24 },
    { label: "2D", val: 48 }, { label: "3D", val: 72 }, { label: "5D", val: 120 }, { label: "7D", val: 168 }
  ],
  DAILY: [
    { label: "1D", val: 24 }, { label: "2D", val: 48 }, { label: "3D", val: 72 }, { label: "5D", val: 120 }, { label: "7D", val: 168 },
    { label: "14D", val: 336 }, { label: "30D", val: 720 }
  ]
};

export default function App() {
  const [inputValue, setInputValue] = useState("");
  const [channels, setChannels] = useState([]);
  const [metadata, setMetadata] = useState({});
  const [aggregates, setAggregates] = useState({});
  const [timeRange, setTimeRange] = useState(1);
  const [granularity, setGranularity] = useState("MINUTELY");
  const [selectedChannel, setSelectedChannel] = useState(null);
  const [errorCount, setErrorCount] = useState(0);
  const MAX_CONSECUTIVE_ERRORS = 3;
  const HOUR_IN_MILLIS = 3600 * 1000;

  useEffect(() => {
    const validOptions = TIME_CONFIG[granularity];
    if (!validOptions.find(o => o.val === timeRange)) {
        setTimeRange(validOptions[0].val);
    }
  }, [granularity]);

  const fetchChannels = async () => {
    const res = await fetch("http://localhost:8080/api/getChannels");
    if (res.ok) {
      const ch = await res.json();
      setChannels(ch);
      if (!selectedChannel && ch.length > 0) setSelectedChannel(ch[0]);
    }
  };

  const fetchChannelsMetadata = async () => {
    const res = await fetch("http://localhost:8080/api/getChannelsMetadata");
    if (res.ok) setMetadata(await res.json());
  };

  const fetchAggregates = useCallback(
    async (list = channels, gran = granularity) => {
      const durationMillis = Number(timeRange) * HOUR_IN_MILLIS;
      const end = Date.now();
      const start = end - durationMillis;
      let newAgg = {};
      let hasError = false;

      for (const channel of list) {
        const url = `http://localhost:8080/api/hitCounter?channelName=${channel}&granularity=${gran}&startTimeMillis=${start}&endTimeMillis=${end}`;
        try {
            const res = await fetch(url);
            if (res.ok) {
                const raw = await res.json();
                const formatted = Object.entries(raw).map(([k, v]) => {
                    const parts = k.split("#");
                    return { ts: parseInt(parts[parts.length - 1], 10), count: v };
                });
                newAgg[channel] = formatted;
            } else if (res.status >= 500) hasError = true;
        } catch (error) { hasError = true; }
      }
      setAggregates(newAgg);
      setErrorCount(hasError ? prev => prev + 1 : 0);
    },
    [channels, granularity, timeRange]
  );

  const addChannel = async (name) => {
    if (!name) return;
    try {
      const res = await fetch(`http://localhost:8080/api/addChannel?channelName=${name}`, { method: "PUT" });
      if (res.ok) fetchChannels();
    } catch (e) { console.error(e); }
  };

  const removeChannel = async (name) => {
    await fetch(`http://localhost:8080/api/removeChannel?channelName=${name}`, { method: "DELETE" });
    const newChannels = channels.filter((ch) => ch !== name);
    setChannels(newChannels);
    if (selectedChannel === name) setSelectedChannel(newChannels.length > 0 ? newChannels[0] : null);
  };

  const chartData = (channel) => {
    if (!aggregates[channel]) return [];
    const durationMillis = Number(timeRange) * HOUR_IN_MILLIS;
    const endTime = Math.floor(Date.now() / 60000) * 60000;
    const startTime = endTime - durationMillis;
    const dataMap = new Map();
    aggregates[channel].forEach(d => dataMap.set(d.ts, d.count));
    const step = granularity === "MINUTELY" ? 60000 : granularity === "HOURLY" ? 3600000 : 86400000;
    const chartDataArray = [];
    for (let t = Math.floor(startTime / step) * step; t <= endTime; t += step) {
      chartDataArray.push({ ts: t, count: dataMap.get(t) || 0 });
    }
    return chartDataArray;
  };

  useEffect(() => { fetchChannels(); fetchChannelsMetadata(); }, []);
  useEffect(() => {
    if (channels.length > 0) fetchAggregates();
    const id = setInterval(() => { if (errorCount < MAX_CONSECUTIVE_ERRORS && channels.length > 0) fetchAggregates(); }, 60000);
    return () => clearInterval(id);
  }, [timeRange, granularity, channels, fetchAggregates, errorCount]);

  return (
    <Box sx={{ display: "flex", height: "100vh", bgcolor: "#f4f5f7", color: "#1a1a1a", flexDirection: "column" }}>
      <Box sx={{ width: "100%", p: 2, bgcolor: "#ffffff", borderBottom: "1px solid #ddd", display: "flex", justifyContent: "center" }}>
        <Typography variant="h4" sx={{ fontWeight: 600 }}>Twitch Chat Hit Counter</Typography>
      </Box>

      <Box sx={{ display: "flex", flexGrow: 1, overflow: "hidden" }}>
        {/* Sidebar */}
        <Box sx={{ width: 320, bgcolor: "#ffffff", borderRight: "1px solid #ddd", p: 2, display: "flex", flexDirection: "column", overflowY: "auto" }}>
          <Typography variant="h5" sx={{ mb: 2, color: "#9146FF" }}>Channels</Typography>
          <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
            <TextField variant="outlined" size="small" placeholder="Add channel..." value={inputValue} onChange={(e) => setInputValue(e.target.value)} sx={{ width: "100%" }} />
            <Button variant="contained" sx={{ bgcolor: "#9146FF" }} onClick={() => { if (inputValue.trim()) { addChannel(inputValue.trim()); setInputValue(""); } }}>Join</Button>
          </Box>
          {channels.map((ch) => (
            <Card key={ch} sx={{ p: 1, display: "flex", alignItems: "center", bgcolor: ch === selectedChannel ? "#d0b1ff" : "#ffffff", borderRadius: 2, cursor: "pointer", mb: 1 }} onClick={() => setSelectedChannel(ch)}>
              <Avatar src={metadata[ch]?.profileImageUrl || ""} />
              <Box sx={{ flexGrow: 1, ml: 1 }}>
                <Typography sx={{ fontWeight: 600 }}>{metadata[ch]?.displayName || ch}</Typography>
                <Typography variant="caption" color="#9146FF">@{ch}</Typography>
              </Box>
              <IconButton onClick={(e) => { e.stopPropagation(); removeChannel(ch); }}><CloseIcon /></IconButton>
            </Card>
          ))}
        </Box>

        {/* Main Panel */}
        <Box sx={{ flexGrow: 1, p: 3, display: "flex", flexDirection: "column" }}>

          {/* 1. Dropdown (Now Above) */}
          <Box sx={{ mb: 2, width: 200 }}>
            <FormControl fullWidth size="small">
              <Select value={granularity} onChange={(e) => setGranularity(e.target.value)}>
                <MenuItem value="MINUTELY">MINUTELY</MenuItem>
                <MenuItem value="HOURLY">HOURLY</MenuItem>
                <MenuItem value="DAILY">DAILY</MenuItem>
              </Select>
            </FormControl>
          </Box>

          {/* 2. Time Buttons (Now Below Dropdown) */}
          <Box sx={{ display: "flex", gap: 1, mb: 3, flexWrap: 'wrap' }}>
            {TIME_CONFIG[granularity].map((opt) => (
              <Button
                key={opt.val}
                variant={timeRange === opt.val ? "contained" : "outlined"}
                onClick={() => setTimeRange(opt.val)}
                sx={{
                    borderColor: "#9146FF",
                    color: timeRange === opt.val ? "white" : "#1a1a1a",
                    bgcolor: timeRange === opt.val ? "#9146FF" : "transparent",
                    textTransform: 'none',
                    minWidth: '60px'
                }}
              >
                {opt.label}
              </Button>
            ))}
          </Box>

          {selectedChannel && (
            <Card sx={{ p: 2, bgcolor: "#ffffff", border: "1px solid #ddd", flexGrow: 1, display: "flex", flexDirection: "column" }}>
              <Box sx={{ display: "flex", alignItems: "center", justifyContent: "center", mb: 2, gap: 1 }}>
                <Avatar src={metadata[selectedChannel]?.profileImageUrl || ""} sx={{ width: 48, height: 48 }} />
                <Typography variant="h5" sx={{ fontWeight: 600 }}>{metadata[selectedChannel]?.displayName || selectedChannel}</Typography>
              </Box>
              <CardContent sx={{ flexGrow: 1, display: "flex", flexDirection: 'column' }}>
                {(() => {
                    const data = chartData(selectedChannel);
                    const totalHits = data.reduce((sum, item) => sum + item.count, 0);
                    const label = timeRange >= 24 ? `${timeRange / 24} day${timeRange/24 !== 1 ? 's' : ''}` : `${timeRange} hour${timeRange !== 1 ? 's' : ''}`;
                    return (
                        <Box sx={{ width: '100%', flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                            <Box sx={{ textAlign: 'center', py: 2 }}>
                                <Typography variant="h3" color="#9146FF" fontWeight={700}>{totalHits.toLocaleString()}</Typography>
                                <Typography variant="h6" color="text.secondary">Total Hits in the last {label}</Typography>
                            </Box>
                            <Box sx={{ flexGrow: 1, minHeight: 300 }}>
                                <ResponsiveContainer width="100%" height="100%">
                                    <LineChart data={data}>
                                        <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                                        <XAxis dataKey="ts" type="number" domain={['auto', 'auto']} tickFormatter={formatTimestamp} height={50} />
                                        <YAxis allowDecimals={false} />
                                        <Tooltip content={<CustomTooltip />} />
                                        <Line type="monotone" dataKey="count" stroke="#9146FF" strokeWidth={2} dot={false} />
                                    </LineChart>
                                </ResponsiveContainer>
                            </Box>
                        </Box>
                    );
                })()}
              </CardContent>
            </Card>
          )}
        </Box>
      </Box>
    </Box>
  );
}

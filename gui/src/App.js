import { useState, useEffect, useCallback } from "react";
import {
  Box,
  Button,
  TextField,
  Typography,
  Avatar,
  IconButton,
  Card,
  CardContent,
  MenuItem,
  Select,
  FormControl,
} from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

// --- Font Import ---
const fontLink = document.createElement("link");
fontLink.href = "https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700;800&display=swap";
fontLink.rel = "stylesheet";
document.head.appendChild(fontLink);

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
      <Box sx={{ p: 1, bgcolor: 'rgba(255, 255, 255, 0.95)', border: '1px solid #ddd', borderRadius: 1, boxShadow: 3 }}>
        <Typography variant="body2" fontWeight="bold" color="#9146FF">Time: {formatTimestamp(label)}</Typography>
        <Typography variant="body2" fontWeight="600">Messages: {payload[0].value.toLocaleString()}</Typography>
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
  }, [granularity, timeRange]);

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
    [channels, granularity, timeRange, HOUR_IN_MILLIS]
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
    <Box sx={{ display: "flex", height: "100vh", width: "100vw", overflow: "hidden", bgcolor: "#f4f5f7", color: "#1a1a1a", fontFamily: "'Inter', sans-serif", flexDirection: "column" }}>

      {/* FIXED HEADER: Removed width: 100% to prevent padding-induced overflow */}
      <Box sx={{
        p: 2,
        bgcolor: "#ffffff",
        borderBottom: "1px solid #ddd",
        display: "flex",
        justifyContent: "center",
        position: "sticky",
        top: 0,
        zIndex: 10,
        boxSizing: 'border-box'
      }}>
        <Typography variant="h4" sx={{ fontWeight: 800, color: "#9146FF", letterSpacing: "-0.02em" }}>
          Twitch Chat Hit Counter
        </Typography>
      </Box>

      <Box sx={{ display: "flex", flexGrow: 1, overflow: "hidden" }}>
        {/* Sidebar */}
        <Box sx={{ width: 320, minWidth: 320, bgcolor: "#ffffff", borderRight: "1px solid #ddd", p: 2, display: "flex", flexDirection: "column", overflowY: "auto" }}>
          <Typography variant="h6" sx={{ mb: 2, fontWeight: 700 }}>Channels</Typography>
          <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
            <TextField variant="outlined" size="small" placeholder="Add channel..." value={inputValue} onChange={(e) => setInputValue(e.target.value)} sx={{ width: "100%" }} />
            <Button variant="contained" sx={{ bgcolor: "#9146FF", fontWeight: 600 }} onClick={() => { if (inputValue.trim()) { addChannel(inputValue.trim()); setInputValue(""); } }}>Join</Button>
          </Box>
          {channels.map((ch) => (
            <Card key={ch} sx={{ p: 1, display: "flex", alignItems: "center", bgcolor: ch === selectedChannel ? "#f0f0ff" : "#ffffff", border: ch === selectedChannel ? "2px solid #9146FF" : "1px solid #eee", borderRadius: 2, cursor: "pointer", mb: 1, transition: '0.2s', '&:hover': { transform: 'translateY(-1px)' } }} onClick={() => setSelectedChannel(ch)}>
              <Avatar src={metadata[ch]?.profileImageUrl || ""} />
              <Box sx={{ flexGrow: 1, ml: 1 }}>
                <Typography sx={{ fontWeight: 700, fontSize: '0.9rem' }}>{metadata[ch]?.displayName || ch}</Typography>
                <Box sx={{ display: 'inline-flex' }}>
                  <Typography component="a" href={`https://www.twitch.tv/${ch}`} target="_blank" rel="noopener noreferrer" onClick={(e) => e.stopPropagation()} sx={{ color: "#9146FF", fontWeight: 600, fontSize: '0.75rem', textDecoration: 'none', '&:hover': { textDecoration: 'underline' } }}>
                    @{ch}
                  </Typography>
                </Box>
              </Box>
              <IconButton size="small" onClick={(e) => { e.stopPropagation(); removeChannel(ch); }}><CloseIcon fontSize="small" /></IconButton>
            </Card>
          ))}
        </Box>

        {/* Main Panel */}
        <Box sx={{ flexGrow: 1, p: 3, display: "flex", flexDirection: "column", overflow: 'hidden' }}>
          <Box sx={{ mb: 1, width: 200 }}>
            <FormControl fullWidth size="small">
              <Select value={granularity} onChange={(e) => setGranularity(e.target.value)} sx={{ fontWeight: 600, borderRadius: '8px' }}>
                <MenuItem value="MINUTELY">MINUTELY</MenuItem>
                <MenuItem value="HOURLY">HOURLY</MenuItem>
                <MenuItem value="DAILY">DAILY</MenuItem>
              </Select>
            </FormControl>
          </Box>

          <Box sx={{ display: "flex", gap: 1, mb: 3 }}>
            {TIME_CONFIG[granularity].map((opt) => (
              <Button key={opt.val} variant={timeRange === opt.val ? "contained" : "outlined"} onClick={() => setTimeRange(opt.val)} sx={{ borderColor: "#9146FF", color: timeRange === opt.val ? "white" : "#1a1a1a", bgcolor: timeRange === opt.val ? "#9146FF" : "transparent", fontWeight: 700, borderRadius: '8px', textTransform: 'none' }}>
                {opt.label}
              </Button>
            ))}
          </Box>

          {selectedChannel && (
            <Card sx={{ p: 3, bgcolor: "#ffffff", borderRadius: '16px', border: "1px solid #ddd", flexGrow: 1, display: "flex", flexDirection: "column", boxShadow: '0 4px 12px rgba(0,0,0,0.05)', overflow: 'hidden' }}>
              <Box sx={{ display: "flex", alignItems: "center", justifyContent: "center", mb: 2, gap: 1.5 }}>
                <Avatar src={metadata[selectedChannel]?.profileImageUrl || ""} sx={{ width: 56, height: 56, border: '2px solid #9146FF' }} />
                <Typography variant="h5" sx={{ fontWeight: 800, letterSpacing: '-0.02em' }}>{metadata[selectedChannel]?.displayName || selectedChannel}</Typography>
              </Box>
              <CardContent sx={{ flexGrow: 1, display: "flex", flexDirection: 'column', p: 0, overflow: 'hidden' }}>
                {(() => {
                  const data = chartData(selectedChannel);
                  const totalHits = data.reduce((sum, item) => sum + item.count, 0);
                  const currentRangeLabel = TIME_CONFIG[granularity].find(o => o.val === timeRange)?.label || `${timeRange}H`;
                  return (
                    <Box sx={{ width: '100%', flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                      <Box sx={{ textAlign: 'center', py: 2 }}>
                        <Typography variant="h2" sx={{ color: "#9146FF", fontWeight: 800, letterSpacing: '-0.03em' }}>{totalHits.toLocaleString()}</Typography>
                        <Typography variant="h6" sx={{ color: "text.secondary", fontWeight: 600 }}>TOTAL CHAT MESSAGES IN LAST {currentRangeLabel}</Typography>
                      </Box>
                      <Box sx={{ flexGrow: 1, minHeight: 400, mt: 2 }}>
                        <ResponsiveContainer width="100%" height="100%">
                          <LineChart data={data}>
                            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
                            <XAxis dataKey="ts" type="number" domain={['auto', 'auto']} tickFormatter={formatTimestamp} height={60} tick={{fontSize: 12, fontWeight: 600}} />
                            <YAxis tick={{fontSize: 12, fontWeight: 600}} axisLine={false} tickLine={false} />
                            <Tooltip content={<CustomTooltip />} />
                            <Line type="monotone" dataKey="count" stroke="#9146FF" strokeWidth={3} dot={false} activeDot={{ r: 6, strokeWidth: 0 }} />
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

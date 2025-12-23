import { useState, useEffect, useCallback, useMemo, useRef } from "react";
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

// Utility function to format timestamp for X-Axis/Tooltip
const formatTimestamp = (timestamp) => {
  const date = new Date(timestamp);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${month}/${day} ${hours}:${minutes}`;
};

// Custom Tooltip component for recharts
const CustomTooltip = ({ active, payload, label }) => {
  if (active && payload && payload.length) {
    return (
      <Box sx={{ p: 1, bgcolor: 'rgba(255, 255, 255, 0.9)', border: '1px solid #ccc', borderRadius: 1, boxShadow: 3 }}>
        <Typography variant="body2" fontWeight="bold" color="#9146FF">
          Time: {formatTimestamp(label)}
        </Typography>
        <Typography variant="body2">
          Hits: {payload[0].value.toLocaleString()}
        </Typography>
      </Box>
    );
  }
  return null;
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
        const MAX_RETRIES = 5;
        let lastError = null;

        for (let attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                const res = await fetch(url);
                if (res.ok) {
                    const raw = await res.json();
                    const formatted = Object.entries(raw).map(([k, v]) => {
                        // FIX: Split by '#' and grab the LAST element to get the timestamp, not the name
                        const parts = k.split("#");
                        const tsString = parts[parts.length - 1];
                        return {
                            ts: parseInt(tsString, 10),
                            count: v,
                        };
                    });
                    newAgg[channel] = formatted;
                    lastError = null;
                    break;
                } else {
                    lastError = new Error(`HTTP ${res.status}`);
                    if (res.status >= 500) hasError = true;
                }
            } catch (error) {
                lastError = error;
                hasError = true;
            }
            if (attempt < MAX_RETRIES - 1) {
                await new Promise(resolve => setTimeout(resolve, Math.pow(2, attempt) * 1000));
            }
        }
      }

      setAggregates(newAgg);
      if (hasError) setErrorCount(prev => prev + 1);
      else setErrorCount(0);
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
//    const endTime = Date.now();
    const endTime = Math.floor(Date.now() / 60000) * 60000;
    const startTime = endTime - durationMillis;

    const dataMap = new Map();
    aggregates[channel].forEach(d => {
      dataMap.set(d.ts, d.count);
    });

    const chartDataArray = [];
    const step = granularity === "MINUTELY" ? 60000 :
                     granularity === "HOURLY" ? 3600000 :
                     granularity === "DAILY" ? 86400000 : 60000;

    for (let t = Math.floor(startTime / step) * step; t <= endTime; t += step) {
      chartDataArray.push({
          ts: t,
          count: dataMap.get(t) || 0
      });
    }
    return chartDataArray;
  };

  const isInitialMount = useRef(true);

  useEffect(() => {
    fetchChannels();
    fetchChannelsMetadata();
  }, []);

  useEffect(() => {
    if (isInitialMount.current) {
        isInitialMount.current = false;
        if (channels.length > 0) fetchAggregates();
    } else {
        if (channels.length > 0) fetchAggregates();
    }
    const id = setInterval(() => {
        if (errorCount < MAX_CONSECUTIVE_ERRORS && channels.length > 0) fetchAggregates();
    }, 60000);
    return () => clearInterval(id);
  }, [timeRange, granularity, channels, fetchAggregates, errorCount]);

  return (
    <Box sx={{ display: "flex", height: "100vh", bgcolor: "#f4f5f7", color: "#1a1a1a", fontFamily: "Roboto, sans-serif", flexDirection: "column" }}>
      <Box sx={{ width: "100%", p: 2, bgcolor: "#ffffff", borderBottom: "1px solid #ddd", display: "flex", justifyContent: "center", alignItems: "center", position: "sticky", top: 0, zIndex: 10, gap: 1 }}>
        <Typography variant="h4" sx={{ fontWeight: 600 }}>Twitch Chat Hit Counter</Typography>
      </Box>

      <Box sx={{ display: "flex", flexGrow: 1, overflow: "hidden" }}>
        <Box sx={{ width: 320, bgcolor: "#ffffff", borderRight: "1px solid #ddd", p: 2, display: "flex", flexDirection: "column", overflowY: "auto" }}>
          <Typography variant="h5" sx={{ mb: 2, color: "#9146FF" }}>Channels</Typography>
          <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
            <TextField variant="outlined" size="small" placeholder="Add channel..." value={inputValue} onChange={(e) => setInputValue(e.target.value)} sx={{ input: { color: "#1a1a1a" }, width: "100%" }} />
            <Button variant="contained" sx={{ bgcolor: "#9146FF" }} onClick={() => { if (inputValue.trim()) { addChannel(inputValue.trim()); setInputValue(""); } }}>Join</Button>
          </Box>
          {channels.map((ch) => (
            <Card key={ch} sx={{ p: 1, display: "flex", alignItems: "center", bgcolor: ch === selectedChannel ? "#d0b1ff" : "#ffffff", borderRadius: 2, boxShadow: "0 4px 8px rgba(0,0,0,0.05), 0 0 0 1px rgba(0,0,0,0.05)", cursor: "pointer", transition: "all 0.2s", mb: 1, "&:hover": { bgcolor: "#d0b1ff", transform: "translateY(-2px)" } }} onClick={() => setSelectedChannel(ch)}>
              <Avatar src={metadata[ch]?.profileImageUrl || ""} />
              <Box sx={{ flexGrow: 1, ml: 1 }}>
                <Typography sx={{ fontWeight: 600 }}>{metadata[ch]?.displayName || ch}</Typography>
                <a href={`https://www.twitch.tv/${ch}`} target="_blank" rel="noopener noreferrer" onClick={(e) => e.stopPropagation()} style={{ color: "#9146FF", textDecoration: "none" }}>@{ch}</a>
              </Box>
              <IconButton onClick={(e) => { e.stopPropagation(); removeChannel(ch); }} sx={{ color: "#9146FF" }}><CloseIcon /></IconButton>
            </Card>
          ))}
        </Box>

        <Box sx={{ flexGrow: 1, p: 3, overflow: "hidden", display: "flex", flexDirection: "column" }}>
          <Box sx={{ display: "flex", gap: 1, mb: 1 }}>
            {[1, 3, 6, 12, 24].map((h) => (
              <Button key={h} variant={timeRange === h ? "contained" : "outlined"} onClick={() => setTimeRange(h)} sx={{ borderColor: "#9146FF", color: timeRange === h ? "white" : "#1a1a1a", bgcolor: timeRange === h ? "#9146FF" : "transparent" }}>{h}h</Button>
            ))}
          </Box>
          <Box sx={{ mb: 2, width: 150 }}>
            <FormControl fullWidth size="small">
              <Select value={granularity} onChange={(e) => setGranularity(e.target.value)}>
                <MenuItem value="MINUTELY">MINUTELY</MenuItem>
                <MenuItem value="HOURLY">HOURLY</MenuItem>
                <MenuItem value="DAILY">DAILY</MenuItem>
              </Select>
            </FormControl>
          </Box>

          {selectedChannel && (
            <Card sx={{ p: 2, bgcolor: "#ffffff", border: "1px solid #ddd", flexGrow: 1, display: "flex", flexDirection: "column" }}>
              <Box sx={{ display: "flex", alignItems: "center", justifyContent: "center", mb: 2, gap: 1 }}>
                <Avatar src={metadata[selectedChannel]?.profileImageUrl || ""} sx={{ width: 48, height: 48 }} />
                <Typography variant="h5" sx={{ fontWeight: 600 }}>{metadata[selectedChannel]?.displayName || selectedChannel}</Typography>
              </Box>
              <CardContent sx={{ flexGrow: 1, width: "100%", minHeight: 300, display: "flex", flexDirection: 'column', alignItems: 'center', justifyContent: 'center', p: 0, '&:last-child': { pb: 0 } }}>
                {errorCount >= MAX_CONSECUTIVE_ERRORS ? (
                    <Box sx={{ textAlign: 'center', p: 4 }}>
                        <Typography variant="h5" color="error" fontWeight={600}>Server Overloaded or Unavailable</Typography>
                        <Typography color="text.secondary">Automatic data fetching paused due to {MAX_CONSECUTIVE_ERRORS} errors.</Typography>
                    </Box>
                ) : (
                    (() => {
                        const data = chartData(selectedChannel);
                        const totalHits = data.reduce((sum, item) => sum + item.count, 0);
                        return (
                            <Box sx={{ width: '100%', flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                                <Box sx={{ textAlign: 'center', py: 2 }}>
                                    <Typography variant="h3" color="#9146FF" fontWeight={700}>{totalHits.toLocaleString()}</Typography>
                                    <Typography variant="h6" color="text.secondary">Total Hits in the last {timeRange} hour{timeRange !== 1 ? 's' : ''}</Typography>
                                </Box>
                                <Box sx={{ flexGrow: 1, minHeight: 300, width: '100%', pt: 2 }}>
                                    <ResponsiveContainer width="100%" height="100%">
                                        <LineChart data={data} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
                                            <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                                            <XAxis dataKey="ts" type="number" domain={['auto', 'auto']} tickFormatter={formatTimestamp} angle={-30} textAnchor="end" height={50} />
                                            <YAxis allowDecimals={false} />
                                            <Tooltip content={<CustomTooltip />} />
                                            <Line type="monotone" dataKey="count" stroke="#9146FF" strokeWidth={2} dot={false} activeDot={{ r: 5 }} />
                                        </LineChart>
                                    </ResponsiveContainer>
                                </Box>
                            </Box>
                        );
                    })()
                )}
              </CardContent>
            </Card>
          )}
        </Box>
      </Box>
    </Box>
  );
}
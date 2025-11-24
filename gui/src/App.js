import { useState, useEffect } from "react";
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
import { LineChart } from "@mui/x-charts/LineChart";
import "./App.css";

export default function App() {
  const [inputValue, setInputValue] = useState("");
  const [channels, setChannels] = useState([]);
  const [metadata, setMetadata] = useState({});
  const [aggregates, setAggregates] = useState({});
  const [timeRange, setTimeRange] = useState(1); // hours
  const [granularity, setGranularity] = useState("MINUTE");
  const [selectedChannel, setSelectedChannel] = useState(null);

  // =========================
  // API CALLS
  // =========================
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

  const fetchAggregates = async (list = channels) => {
    let newAgg = {};
    for (const channel of list) {
      const res = await fetch(
        `http://localhost:8080/api/hitCounter?channelName=${channel}`
      );
      if (res.ok) {
        const raw = await res.json();
        const formatted = Object.entries(raw).map(([k, v]) => ({
          minuteTs: parseInt(k.split("#")[1], 10),
          count: v,
        }));
        newAgg[channel] = formatted;
      }
    }
    setAggregates(newAgg);
  };

  const addChannel = async (name) => {
    if (!name) return;
    await fetch(`http://localhost:8080/api/addChannel?channelName=${name}`, {
      method: "PUT",
    });
    fetchChannels();
  };

  const removeChannel = async (name) => {
    await fetch(`http://localhost:8080/api/removeChannel?channelName=${name}`, {
      method: "DELETE",
    });

    // Filter out the removed channel
    const newChannels = channels.filter((ch) => ch !== name);
    setChannels(newChannels);

    // Auto-select first channel if current removed channel was selected
    if (selectedChannel === name) {
      setSelectedChannel(newChannels.length > 0 ? newChannels[0] : null);
    }
  };

  // =========================
  // DATA FORMATTERS
  // =========================
  const chartData = (channel) => {
    if (!aggregates[channel]) return { x: [], y: [] };

    const now = Date.now();
    const cutoff = now - timeRange * 3600 * 1000;

    const dataMap = {};
    aggregates[channel].forEach((d) => {
      if (d.minuteTs >= cutoff) {
        const ts = Math.floor(d.minuteTs / 60000) * 60000;
        dataMap[ts] = d.count;
      }
    });

    const x = [];
    const y = [];
    for (let t = Math.floor(cutoff / 60000) * 60000; t <= now; t += 60000) {
      x.push(t);
      y.push(dataMap[t] || 0);
    }

    return { x, y };
  };

  // =========================
  // EFFECTS
  // =========================
  useEffect(() => {
    fetchChannels();
  }, []);

  useEffect(() => {
    if (channels.length > 0) {
      fetchChannelsMetadata();
      fetchAggregates();
    }
  }, [channels]);

  useEffect(() => {
    const id = setInterval(fetchAggregates, 60000);
    return () => clearInterval(id);
  }, [timeRange, channels]);

  // =========================
  // UI
  // =========================
  return (
    <Box
      sx={{
        display: "flex",
        height: "100vh",
        bgcolor: "#f4f5f7",
        color: "#1a1a1a",
        fontFamily: "Roboto, sans-serif",
        flexDirection: "column",
      }}
    >
      {/* HEADER */}
      <Box
        sx={{
          width: "100%",
          p: 2,
          bgcolor: "#ffffff",
          borderBottom: "1px solid #ddd",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          position: "sticky",
          top: 0,
          zIndex: 10,
          gap: 1,
        }}
      >
        <img
          src={`${process.env.PUBLIC_URL}/twitchLogo.png`}
          alt="Twitch Logo"
          style={{ height: 32 }}
        />
        <Typography variant="h4" sx={{ fontWeight: 600 }}>
          Chat Hit Counter
        </Typography>
      </Box>

      <Box sx={{ display: "flex", flexGrow: 1, overflow: "hidden" }}>
        {/* SIDEBAR */}
        <Box
          sx={{
            width: 320,
            bgcolor: "#ffffff",
            borderRight: "1px solid #ddd",
            p: 2,
            display: "flex",
            flexDirection: "column",
            overflowY: "auto",
          }}
        >
          <Typography variant="h5" sx={{ mb: 2, color: "#9146FF" }}>
            Channels
          </Typography>

          <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
            <TextField
              variant="outlined"
              size="small"
              placeholder="Add channel..."
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              sx={{ input: { color: "#1a1a1a" }, width: "100%" }}
            />
            <Button
              variant="contained"
              sx={{ bgcolor: "#9146FF" }}
              onClick={() => {
                if (inputValue.trim()) {
                  addChannel(inputValue.trim());
                  setInputValue("");
                }
              }}
            >
              Join
            </Button>
          </Box>

          {channels.map((ch) => (
            <Card
              key={ch}
              sx={{
                p: 1,
                display: "flex",
                alignItems: "center",
                bgcolor: ch === selectedChannel ? "#d0b1ff" : "#ffffff",
                borderRadius: 2,
                boxShadow:
                  "0 4px 8px rgba(0,0,0,0.05), 0 0 0 1px rgba(0,0,0,0.05)",
                cursor: "pointer",
                transition: "all 0.2s",
                mb: 1,
                "&:hover": { bgcolor: "#d0b1ff", transform: "translateY(-2px)" },
              }}
              onClick={() => setSelectedChannel(ch)}
            >
              <Avatar src={metadata[ch]?.profileImageUrl || ""} />
              <Box sx={{ flexGrow: 1, ml: 1 }}>
                <Typography sx={{ fontWeight: 600 }}>
                  {metadata[ch]?.displayName || ch}
                </Typography>
                <a
                  href={`https://www.twitch.tv/${ch}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  onClick={(e) => e.stopPropagation()}
                  style={{ color: "#9146FF", textDecoration: "none" }}
                >
                  @{ch}
                </a>
              </Box>
              <IconButton
                onClick={(e) => {
                  e.stopPropagation();
                  removeChannel(ch);
                }}
              >
                <img
                  src={`${process.env.PUBLIC_URL}/peepoLeave.gif`}
                  alt="Remove"
                  style={{ width: 28, height: 28 }}
                />
              </IconButton>
            </Card>
          ))}
        </Box>

        {/* MAIN CHART */}
        <Box
          sx={{
            flexGrow: 1,
            p: 3,
            overflow: "hidden",
            display: "flex",
            flexDirection: "column",
          }}
        >
          {/* Time range buttons */}
          <Box sx={{ display: "flex", gap: 1, mb: 1 }}>
            {[1, 3, 6, 12, 24].map((h) => (
              <Button
                key={h}
                variant={timeRange === h ? "contained" : "outlined"}
                onClick={() => setTimeRange(h)}
                sx={{
                  borderColor: "#9146FF",
                  color: timeRange === h ? "white" : "#1a1a1a",
                  bgcolor: timeRange === h ? "#9146FF" : "transparent",
                }}
              >
                {h}h
              </Button>
            ))}
          </Box>

          {/* Granularity dropdown */}
          <Box sx={{ mb: 2, width: 150 }}>
            <FormControl fullWidth size="small">
              <Select
                value={granularity}
                onChange={(e) => setGranularity(e.target.value)}
              >
                <MenuItem value="MINUTE">MINUTE</MenuItem>
                <MenuItem value="HOURLY" disabled>
                  HOURLY
                </MenuItem>
                <MenuItem value="DAILY" disabled>
                  DAILY
                </MenuItem>
              </Select>
            </FormControl>
          </Box>

          {selectedChannel && (
            <Card
              sx={{
                p: 2,
                bgcolor: "#ffffff",
                border: "1px solid #ddd",
                flexGrow: 1,
                display: "flex",
                flexDirection: "column",
              }}
            >
              {/* Chart Header: Centered Avatar + Name */}
              <Box
                sx={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                  mb: 2,
                  gap: 1,
                }}
              >
                <Avatar
                  src={metadata[selectedChannel]?.profileImageUrl || ""}
                  sx={{ width: 48, height: 48 }}
                />
                <Typography variant="h5" sx={{ fontWeight: 600 }}>
                  {metadata[selectedChannel]?.displayName || selectedChannel}
                </Typography>
              </Box>

              <CardContent
                sx={{
                  flexGrow: 1,
                  width: "100%",
                  minHeight: 300,
                  display: "flex",
                }}
              >
                <LineChart
                  width={undefined}
                  height={undefined}
                  xAxis={[
                    {
                      data: chartData(selectedChannel).x,
                      scaleType: "time",
                      valueFormatter: (ts) =>
                        new Date(ts).toLocaleTimeString(),
                    },
                  ]}
                  series={[
                    {
                      data: chartData(selectedChannel).y,
                      label: "Hits",
                      color: "#9146FF",
                      curve: "monotoneX",
                      showMark: () => false,
                    },
                  ]}
                  margin={{ left: 60, right: 20, top: 20, bottom: 40 }}
                  sx={{ flexGrow: 1 }}
                />
              </CardContent>
            </Card>
          )}
        </Box>
      </Box>
    </Box>
  );
}

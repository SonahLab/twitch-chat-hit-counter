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

// Import the DeleteIcon from Material-UI
import DeleteIcon from '@mui/icons-material/Delete';

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
  // Format based on granularity. Since we only use MINUTELY, we show time.
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
  const [timeRange, setTimeRange] = useState(1); // hours (User controlled)

  // Frontend value changed to 'MINUTELY' to match backend requirements
  const [granularity, setGranularity] = useState("MINUTELY");

  const [selectedChannel, setSelectedChannel] = useState(null);

  // Circuit Breaker State
  const [errorCount, setErrorCount] = useState(0);
  const MAX_CONSECUTIVE_ERRORS = 3;

  // Constants for time calculations
  const HOUR_IN_MILLIS = 3600 * 1000;

  // =========================
  // API CALLS (Stabilized by calculating time boundaries internally)
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

  /**
   * REVISED: Uses explicit calculation with Number() to ensure consistent start time.
   */
  const fetchAggregates = useCallback(
    async (
      list = channels,
      gran = granularity,
    ) => {
      // Calculate time dynamically inside the stable useCallback
      const durationMillis = Number(timeRange) * HOUR_IN_MILLIS; // <-- Explicitly ensure timeRange is treated as a number
      const end = Date.now();
      const start = end - durationMillis;

      let newAgg = {};
      let hasError = false;

      for (const channel of list) {
        // Use the calculated start and end times
        const url = `http://localhost:8080/api/hitCounter?channelName=${channel}&granularity=${gran}&startTimeMillis=${start}&endTimeMillis=${end}`;

        const MAX_RETRIES = 5;
        let lastError = null;

        for (let attempt = 0; attempt < MAX_RETRIES; attempt++) {
            try {
                const res = await fetch(url);

                if (res.ok) {
                    const raw = await res.json();
                    const formatted = Object.entries(raw).map(([k, v]) => {
                        const tsString = k.includes('#') ? k.split("#")[1] : k;
                        return {
                            ts: parseInt(tsString, 10),
                            count: v,
                        };
                    });
                    newAgg[channel] = formatted;
                    lastError = null;
                    break;
                } else {
                    console.error(`Attempt ${attempt + 1}: HTTP error for channel ${channel}: ${res.status} ${res.statusText}`);
                    lastError = new Error(`HTTP ${res.status}`);
                    if (res.status >= 500) hasError = true;
                }
            } catch (error) {
                console.error(`Attempt ${attempt + 1}: Network or Parse error for channel ${channel}:`, error);
                lastError = error;
                hasError = true;
            }

            if (attempt < MAX_RETRIES - 1) {
                const delay = Math.pow(2, attempt) * 1000;
                await new Promise(resolve => setTimeout(resolve, delay));
            }
        }

        if (lastError) {
            console.error(`Failed to fetch aggregates for channel ${channel} after ${MAX_RETRIES} attempts.`, lastError);
        }
      }

      setAggregates(newAgg);

      if (hasError) {
          setErrorCount(prev => prev + 1);
          console.warn(`Circuit Breaker: Error Count incremented to ${errorCount + 1}.`);
      } else {
          setErrorCount(0);
      }
    },
    // timeRange is included here. When timeRange changes (user click), fetchAggregates
    // is redefined, which correctly triggers the main useEffect.
    [channels, granularity, errorCount, timeRange, HOUR_IN_MILLIS]
  );

  const addChannel = async (name) => {
    if (!name) return;
    const MAX_RETRIES = 5;
    let lastError = null;

    for (let attempt = 0; attempt < MAX_RETRIES; attempt++) {
      try {
        const res = await fetch(`http://localhost:8080/api/addChannel?channelName=${name}`, {
          method: "PUT",
        });
        if (res.ok) {
          lastError = null;
          break;
        } else {
          console.error(`Attempt ${attempt + 1}: HTTP error on adding channel ${name}: ${res.status} ${res.statusText}`);
          lastError = new Error(`HTTP ${res.status}`);
        }
      } catch (error) {
        console.error(`Attempt ${attempt + 1}: Network error on adding channel ${name}:`, error);
        lastError = error;
      }

      if (attempt < MAX_RETRIES - 1) {
          const delay = Math.pow(2, attempt) * 1000;
          await new Promise(resolve => setTimeout(resolve, delay));
      }
    }

    if (lastError) {
      console.error(`Failed to add channel ${name} after ${MAX_RETRIES} attempts.`, lastError);
    } else {
      fetchChannels();
    }
  };

  const removeChannel = async (name) => {
    await fetch(`http://localhost:8080/api/removeChannel?channelName=${name}`, {
      method: "DELETE",
    });

    const newChannels = channels.filter((ch) => ch !== name);
    setChannels(newChannels);

    if (selectedChannel === name) {
      setSelectedChannel(newChannels.length > 0 ? newChannels[0] : null);
    }
  };

  // =========================
  // DATA FORMATTERS (REVISED for recharts compatibility)
  // =========================
  /**
   * Generates a padded array of objects suitable for recharts:
   * [{ ts: 1700000000000, count: 10 }, { ts: 1700000060000, count: 5 }, ...]
   * REVISED: Uses explicit calculation with Number() to ensure consistent start time.
   */
  const chartData = (channel) => {
    if (!aggregates[channel]) return [];

    // Calculate time boundaries for chart rendering (must match fetch logic)
    const durationMillis = Number(timeRange) * HOUR_IN_MILLIS; // <-- Explicitly ensure timeRange is treated as a number
    const endTime = Date.now();
    const startTime = endTime - durationMillis;

    const dataMap = new Map();
    // Aggregates array contains { ts: timestamp, count: value } objects
    aggregates[channel].forEach(d => {
      dataMap.set(d.ts, d.count);
    });

    const chartDataArray = [];

    const step = granularity === "MINUTELY" ? 60000 :
                 granularity === "HOURLY" ? 3600000 :
                 granularity === "DAILY" ? 86400000 : 60000;

    // Use the locally calculated startTime for padding
    // Math.floor(startTime / step) * step ensures we start at a clean time bucket boundary
    for (let t = Math.floor(startTime / step) * step; t <= endTime; t += step) {
      chartDataArray.push({
          ts: t, // Timestamp for X-Axis (recharts data key)
          count: dataMap.get(t) || 0 // Count for Y-Axis (recharts data key)
      });
    }

    return chartDataArray;
  };

  // =========================
  // EFFECTS
  // =========================
  const isInitialMount = useRef(true);

  // Initial setup: channels and metadata fetching (runs only once)
  useEffect(() => {
    fetchChannels();
    fetchChannelsMetadata();
  }, []);

  // Main data fetching logic with stable dependencies
  useEffect(() => {
    // This log runs whenever the effect dependencies change (user action or interval reset)
    console.log(`[FRONTEND FETCH] Effect Re-run triggered. Granularity: ${granularity}. Time Range: ${timeRange}h.`);

    if (errorCount >= MAX_CONSECUTIVE_ERRORS) {
        console.error(`Fetching disabled: Max consecutive errors (${MAX_CONSECUTIVE_ERRORS}) reached.`);
        return () => {};
    }

    // Logic to prevent immediate execution on initial render
    if (isInitialMount.current) {
        isInitialMount.current = false;
        if (channels.length > 0) {
           console.log(`[FRONTEND FETCH] Initial Mount with channels available. Triggering Data Refresh.`);
           // Call fetchAggregates without explicit time arguments
           fetchAggregates();
        }
    } else {
        // This runs only when a dependency (channels, timeRange, granularity, or fetchAggregates identity) changes AFTER mount.
        console.log(`[FRONTEND FETCH] Dependency-Change Fetch triggered (Data Refresh). Granularity: ${granularity}.`);

        if (channels.length > 0) {
            // Call fetchAggregates without explicit time arguments
            fetchAggregates();
        }
    }

    // Set up interval for refreshing the data (once per minute)
    const id = setInterval(() => {
        if (errorCount < MAX_CONSECUTIVE_ERRORS && channels.length > 0) {
            // LOG 2: Interval Fetch
            console.log(`[FRONTEND FETCH] Interval Fetch triggered (every 60s). Granularity: ${granularity}.`);
            // Call fetchAggregates without explicit time arguments
            fetchAggregates();
        } else if (errorCount >= MAX_CONSECUTIVE_ERRORS) {
            // If the circuit is broken, clear the interval
            clearInterval(id);
        }
    }, 60000); // 60 seconds interval

    // Cleanup function
    return () => clearInterval(id);

  // The dependencies are now stable state/props that only change due to user actions (timeRange, granularity, channels)
  // or internal logic (fetchAggregates identity change, errorCount). No more Date.now() derivatives.
  }, [timeRange, granularity, channels, fetchAggregates, errorCount, HOUR_IN_MILLIS]);


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
        <Typography variant="h4" sx={{ fontWeight: 600 }}>
          Twitch Chat Hit Counter
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
                // Replace the gif with Material-UI DeleteIcon
                sx={{ color: "#9146FF" }} // Apply Twitch purple color
              >
                <DeleteIcon />
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
                <MenuItem value="MINUTELY">MINUTELY</MenuItem>
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
                  flexDirection: 'column',
                  alignItems: 'center',
                  justifyContent: 'center',
                  p: 0, // Remove default CardContent padding
                  '&:last-child': { pb: 0 } // Remove bottom padding
                }}
              >
                {/* Display an error message if the circuit is broken */}
                {errorCount >= MAX_CONSECUTIVE_ERRORS ? (
                    <Box sx={{ textAlign: 'center', p: 4 }}>
                        <Typography variant="h5" color="error" fontWeight={600}>
                            Server Overloaded or Unavailable
                        </Typography>
                        <Typography color="text.secondary">
                            Automatic data fetching has been paused due to {MAX_CONSECUTIVE_ERRORS} consecutive errors.
                            Please wait a moment and refresh or try again later.
                        </Typography>
                    </Box>
                ) : (
                    // Hit counter display and new Graphing Feature
                    (() => {
                        const data = chartData(selectedChannel);
                        const totalHits = data.reduce((sum, item) => sum + item.count, 0);

                        return (
                            <Box sx={{ width: '100%', flexGrow: 1, display: 'flex', flexDirection: 'column' }}>
                                {/* Total Hits Counter */}
                                <Box sx={{ textAlign: 'center', py: 2 }}>
                                    <Typography variant="h3" color="#9146FF" fontWeight={700}>
                                        {totalHits.toLocaleString()}
                                    </Typography>
                                    <Typography variant="h6" color="text.secondary">
                                        Total Hits in the last {timeRange} hour{timeRange !== 1 ? 's' : ''}
                                    </Typography>
                                </Box>

                                {/* Chart Area */}
                                <Box sx={{ flexGrow: 1, minHeight: 300, width: '100%', pt: 2 }}>
                                    <ResponsiveContainer width="100%" height="100%">
                                        <LineChart
                                            data={data}
                                            margin={{ top: 5, right: 20, left: 0, bottom: 5 }}
                                        >
                                            <CartesianGrid strokeDasharray="3 3" stroke="#e0e0e0" />
                                            <XAxis
                                                dataKey="ts"
                                                type="number"
                                                domain={['auto', 'auto']}
                                                tickFormatter={formatTimestamp}
                                                // Rotate ticks for better legibility on longer date ranges
                                                angle={-30}
                                                textAnchor="end"
                                                height={50}
                                            />
                                            <YAxis allowDecimals={false} />
                                            <Tooltip content={<CustomTooltip />} />
                                            <Line
                                                type="monotone"
                                                dataKey="count"
                                                stroke="#9146FF"
                                                strokeWidth={2}
                                                dot={false}
                                                name="Chat Hits"
                                                activeDot={{ r: 5 }}
                                            />
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

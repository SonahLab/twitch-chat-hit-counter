import logo from './logo.svg';
import './App.css';
import { FaTwitch } from "react-icons/fa";
import React, { useEffect, useState } from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, ReferenceLine } from 'recharts';


function App() {
  // var for search input
  const [inputValue, setInputValue] = useState('');
  // var list of channels we're listening to
  const [channels, setChannels] = useState([]);
  const [loading, setLoading] = useState(false);
  // var (dict) to hold the minutely aggregates {channel: {channel#minuteTs: count}}
  const [aggregates, setAggregates] = useState({});
  // var (int) to control the granularity of the graphs
  const [timeRange, setTimeRange] = useState(1);



  // BACKEND API CALLS
  // =========================================================================================================================
  // =================================================== /api/addChannel =====================================================
  // =========================================================================================================================
  // Add a channel to the backend
  const addChannel = async (channelName) => {
    try {
      const response = await fetch(`http://localhost:8080/api/addChannel?channelName=${channelName}`, {
        method: 'PUT',
      });

      if (response.ok) {
        fetchChannels();
        fetchAggregates([channelName], timeRange);
        alert(`Successfully joined the channel: ${channelName}`);
      } else if (response.status === 404) {
        alert(`Channel "${channelName}" does not exist!`);
        console.error('Failed to add channel');
      } else {
        alert('Something went wrong. Please try again later.');
      }
    } catch (error) {
      console.error('Error adding channel:', error);
    }
  };



  // =========================================================================================================================
  // =================================================== /api/getChannels ====================================================
  // =========================================================================================================================
  // Load all streamer 'channels' that we've joined/listening on from the backend's REDIS DB through the HTTP endpoint
  const fetchChannels = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/getChannels', {
        method: 'GET',
      });

      if (response.ok) {
        const data = await response.json();
        setChannels(data);
        alert('Channels fetched successfully: ' + JSON.stringify(data));
      } else {
        console.error('Failed to fetch channels');
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };



  // =========================================================================================================================
  // =================================================== /api/removeChannel ==================================================
  // =========================================================================================================================
  // Remove a streamer that we've joined/listening on from the backend's REDIS DB through the HTTP endpoint
  const removeChannel = async (channelName) => {
    try {
      const response = await fetch(`http://localhost:8080/api/removeChannel?channelName=${channelName}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        setChannels((prevChannels) =>
          prevChannels.filter((channel) => channel !== channelName)
        );
        // Remove aggregates for this channel as well
        setAggregates((prevAggregates) => {
          const { [channelName]: _, ...rest } = prevAggregates;
          return rest;
        });
      } else {
        console.error('Failed to remove channel');
      }
    } catch (error) {
      console.error('Error removing channel:', error);
    }
  };



  // =========================================================================================================================
  // =================================================== /api/hitCounter =====================================================
  // =========================================================================================================================
  // Fetch the aggregated data (minutely counts) for each channel
  const fetchAggregates = async (channelsList, range) => {
    const newAggregates = { ...aggregates };
    const fullTimeRange = [];

    // Step 1: Determine the full time range for all channels
    for (const channelName of channelsList) {
      try {
        const response = await fetch(`http://localhost:8080/api/hitCounter?channelName=${channelName}`, {
          method: 'GET',
        });

        if (response.ok) {
          const data = await response.json();
  
          // Format the data to { minuteTs: count }
          const formattedData = Object.entries(data).map(([key, count]) => {
            const minuteTs = key.split('#')[1];
            return { minuteTs: parseInt(minuteTs, 10), count };
          });
  
          // Filter data by time range (using the passed `range` instead of state)
          const filteredData = filterDataByTimeRange(formattedData, range);
          newAggregates[channelName] = filteredData;

          // Add timestamps from this channel to the full time range
          filteredData.forEach(item => {
            if (!fullTimeRange.includes(item.minuteTs)) {
              fullTimeRange.push(item.minuteTs);
            }
          });
        } else {
          console.error(`Failed to fetch aggregates for channel: ${channelName}`);
        }
      } catch (error) {
        console.error(`Error fetching aggregates for channel ${channelName}:`, error);
      }
    }
  
    // Step 2: Sort the full time range (for X-axis alignment)
    fullTimeRange.sort((a, b) => a - b);

    // Step 3: Fill missing data for each channel (make sure every channel has data for the full time range)
    for (const channelName in newAggregates) {
      const channelData = newAggregates[channelName];
      const filledData = fullTimeRange.map(time => {
        const existingData = channelData.find(item => item.minuteTs === time);
        return existingData ? existingData : { minuteTs: time, count: 0 };
      });
      newAggregates[channelName] = filledData;
    }
    setAggregates(newAggregates);
  };



  // FRONTEND RELATED HELPERS
  // Change inputValue var based on what user types into search box
  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  // Calls addChannel backend Endpoint
  const handleSubmit = () => {
    alert(`Listening to ${inputValue}'s TwitchChat`);
    addChannel(inputValue);
    setInputValue('');
  };
  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      // event.preventDefault();
      handleSubmit();
    }
  };

  // Filter data for points that are within the 'Past X Hours' boundary
  const filterDataByTimeRange = (data, hours) => {
    const now = Date.now();
    const timeLimit = now - hours * 60 * 60 * 1000;
    return data.filter(item => item.minuteTs >= timeLimit);
  };

  // Prepare the data for plotting
  const prepareChartData = (channelName) => {
    if (!aggregates[channelName]) return [];
  
    const chartData = aggregates[channelName]
      .sort((a, b) => a.minuteTs - b.minuteTs)
      .map(item => {
        const formattedDate = new Date(item.minuteTs).toLocaleString('en-CA', {
          year: 'numeric', 
          month: '2-digit', 
          day: '2-digit', 
          hour: '2-digit', 
          minute: '2-digit',
          hour12: false,  // 24-hour format
        });
  
        return {
          minuteTs: item.minuteTs,
          formattedDate, // Needed for the Tooltip below to display TS in readable format
          count: item.count,
        };
      });
  
    console.log(`Chart data for ${channelName}:`, chartData);
  
    return chartData;
  };

  // Update graphs' aggregate data shown to trigger on button 'Past X Hours' click
  const handleTimeRangeChange = (newTimeRange) => {
    setTimeRange(newTimeRange);
    if (channels.length > 0) {
      fetchAggregates(channels, newTimeRange);
    }
  };

  useEffect(() => {
    const fetchChannels = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/api/getChannels');
        if (response.ok) {
          const data = await response.json();
          setChannels(data);

          // Fetch aggregates data for the default time range (1 hour) after loading the channels
          fetchAggregates(data, timeRange); // Pass timeRange here
        } else {
          console.error('Failed to fetch channels');
        }
      } catch (error) {
        console.error('Error fetching channels:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchChannels();
  }, [timeRange]); 

  // Refresh data every minute (keeping the same timeRange)
  useEffect(() => {
    const intervalId = setInterval(() => {
      if (channels.length > 0) {
        fetchAggregates(channels, timeRange);
      }
    }, 60000);

    return () => clearInterval(intervalId); // Clean up the interval when the component unmounts
  }, [channels, timeRange]); // The interval depends on channels and timeRange
  



  // UI LAYOUT
  return (
    <div className="App flex-vertically-center">
      <div className="flex-container flex-horizontally-center">
        <div className="App-home">{"Twitch Chat Hit Counter"}</div>
        {/* Input text box to join a streamer's channel */}
        <input 
          type="text" 
          value={inputValue}
          onChange={handleInputChange}
          onKeyDown={handleKeyDown}
          placeholder="Streamer's Channel..."
        />
        {/* Join button to add the streamer's channel to Redis and start ingesting/aggregation on streamer's twitch chat */}
        <button onClick={handleSubmit} className="submit-button">
          Join
        </button>
      </div>

      {/* Loading Spinner */}
      {loading && <div>Loading...</div>}

      <div className="App-home">Joined Channels</div>
      {/* Grid View of Channels */}
      <div className="grid-container">
        {channels.length > 0 ? (
          channels.map((channel, index) => (
            <div className="grid-item" key={index}>
              {/* Use the FaTwitch icon as a placeholder for the image */}
              <FaTwitch size={30} color="#9146FF" /> {/* Twitch logo */}

              {/* Close Button (X) */}
              <button
                className="remove-button"
                onClick={() => removeChannel(channel)}
              >
                X
              </button>

              {/* Channel Name */}
              <div className="channel-name">{channel}</div>
            </div>
          ))
        ) : (
          <div>No channels joined yet.</div>
        )}
      </div>

      {/* New Section for Graphs */}
      <div className="charts-section">
        {channels.length > 0 && (
          <div>
            <h2>Live Chat Minutely Stats for Joined Channels</h2>
            <div className="time-range-selector">
              <button onClick={() => handleTimeRangeChange(1)} className={timeRange === 1 ? "active" : ""}>
                Last 1 Hour
              </button>
              <button onClick={() => handleTimeRangeChange(3)} className={timeRange === 3 ? "active" : ""}>
                Last 3 Hours
              </button>
              <button onClick={() => handleTimeRangeChange(6)} className={timeRange === 6 ? "active" : ""}>
                Last 6 Hours
              </button>
              <button onClick={() => handleTimeRangeChange(12)} className={timeRange === 12 ? "active" : ""}>
                Last 12 Hours
              </button>
              <button onClick={() => handleTimeRangeChange(24)} className={timeRange === 24 ? "active" : ""}>
                Last 24 Hours
              </button>
            </div>

            {channels.map((channel, index) => (
              <div key={index}>
                <h3>{channel}</h3>
                <ResponsiveContainer width="100%" height={300}>
                  <LineChart data={prepareChartData(channel)}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis 
                      dataKey="minuteTs"
                      tickFormatter={(value) => new Date(value).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                    />

                    <YAxis />
                    <Tooltip 
                      content={({ payload }) => {
                        if (!payload || payload.length === 0) return null;

                        const { formattedDate, count } = payload[0].payload;

                        return (
                          <div className="custom-tooltip">
                            <p>{formattedDate}</p> {/* Display the formatted timestamp */}
                            <p>Count: {count}</p>
                          </div>
                        );
                      }} 
                    />
                    <Legend />
                    <Line type="monotone" dataKey="count" stroke="#8884d8" />
                  </LineChart>
                </ResponsiveContainer>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;

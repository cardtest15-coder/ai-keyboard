const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');
const mongoose = require('mongoose');
const http = require('http');
const { Server } = require('socket.io');

dotenv.config();

const app = express();
const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: "*",
    methods: ["GET", "POST"]
  }
});

app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost:27017/aikeyboard', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => console.log('MongoDB connected'))
  .catch(err => console.error('MongoDB connection error:', err));

app.use('/api/auth', require('./routes/auth'));
app.use('/api/predictions', require('./routes/predictions'));
app.use('/api/autocorrect', require('./routes/autocorrect'));
app.use('/api/speech', require('./routes/speech'));
app.use('/api/gestures', require('./routes/gestures'));
app.use('/api/translations', require('./routes/translations'));
app.use('/api/shortcuts', require('./routes/shortcuts'));
app.use('/api/templates', require('./routes/templates'));
app.use('/api/analytics', require('./routes/analytics'));

io.on('connection', (socket) => {
  console.log('Client connected:', socket.id);
  
  socket.on('prediction-request', async (data) => {
    const { text, language } = data;
    const predictions = await require('./services/predictionService').getPredictions(text, language);
    socket.emit('prediction-response', predictions);
  });
  
  socket.on('disconnect', () => {
    console.log('Client disconnected:', socket.id);
  });
});

const PORT = process.env.PORT || 5000;
server.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
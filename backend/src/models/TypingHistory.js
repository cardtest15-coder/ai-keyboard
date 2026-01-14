const mongoose = require('mongoose');

const typingHistorySchema = new mongoose.Schema({
  userId: { type: String, required: true },
  text: { type: String, required: true },
  context: { type: String },
  app: { type: String },
  timestamp: { type: Date, default: Date.now },
  wpm: { type: Number },
  corrections: [{ original: String, corrected: String }]
});

module.exports = mongoose.model('TypingHistory', typingHistorySchema);
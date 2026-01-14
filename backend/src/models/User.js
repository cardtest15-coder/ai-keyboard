const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  userId: { type: String, required: true, unique: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  preferences: {
    language: { type: String, default: 'en' },
    theme: { type: String, default: 'dark' },
    hapticFeedback: { type: Boolean, default: true },
    soundEnabled: { type: Boolean, default: false },
    autoCorrectEnabled: { type: Boolean, default: true },
    predictiveTextEnabled: { type: Boolean, default: true }
  },
  typingStats: {
    wordsTyped: { type: Number, default: 0 },
    charactersTyped: { type: Number, default: 0 },
    avgWPM: { type: Number, default: 0 },
    accuracy: { type: Number, default: 0 }
  },
  createdAt: { type: Date, default: Date.now },
  lastActive: { type: Date, default: Date.now }
});

module.exports = mongoose.model('User', userSchema);
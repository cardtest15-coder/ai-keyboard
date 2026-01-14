const mongoose = require('mongoose');

const templateSchema = new mongoose.Schema({
  userId: { type: String, required: true },
  name: { type: String, required: true },
  content: { type: String, required: true },
  tags: [String],
  usageCount: { type: Number, default: 0 },
  isPublic: { type: Boolean, default: false },
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Template', templateSchema);
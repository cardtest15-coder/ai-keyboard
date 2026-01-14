const express = require('express');
const router = express.Router();
const User = require('../models/User');
const TypingHistory = require('../models/TypingHistory');

router.get('/stats', async (req, res) => {
  try {
    const { userId } = req.query;
    
    const user = await User.findOne({ userId });
    if (!user) {
      return res.status(404).json({ error: 'User not found' });
    }
    
    const history = await TypingHistory.find({ userId }).sort({ timestamp: -1 }).limit(100);
    
    const stats = {
      typingStats: user.typingStats,
      recentActivity: history.slice(0, 10),
      popularApps: this.getPopularApps(history),
      averageTypingSpeed: this.calculateAverageSpeed(history)
    };
    
    res.json(stats);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/log', async (req, res) => {
  try {
    const { userId, text, app, wpm, corrections } = req.body;
    
    const history = new TypingHistory({
      userId,
      text,
      app,
      wpm,
      corrections
    });
    
    await history.save();
    
    await User.findOneAndUpdate(
      { userId },
      {
        $inc: {
          'typingStats.wordsTyped': text.split(' ').length,
          'typingStats.charactersTyped': text.length
        },
        lastActive: new Date()
      }
    );
    
    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
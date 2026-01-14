const express = require('express');
const router = express.Router();

router.post('/recognize', async (req, res) => {
  try {
    const { gestureData } = req.body;
    
    const gestures = {
      'swipe_left': 'space',
      'swipe_right': 'delete',
      'swipe_up': 'capital',
      'swipe_down': 'enter',
      'double_tap': 'period'
    };
    
    const recognized = gestures[gestureData.type] || 'unknown';
    
    res.json({
      gesture: gestureData.type,
      action: recognized,
      confidence: 0.9
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/types', (req, res) => {
  const gestureTypes = [
    { type: 'swipe_left', description: 'Swipe left for space' },
    { type: 'swipe_right', description: 'Swipe right to delete' },
    { type: 'swipe_up', description: 'Swipe up for capital letter' },
    { type: 'swipe_down', description: 'Swipe down for enter' },
    { type: 'double_tap', description: 'Double tap for period' },
    { type: 'long_press', description: 'Long press for special characters' }
  ];
  
  res.json(gestureTypes);
});

module.exports = router;
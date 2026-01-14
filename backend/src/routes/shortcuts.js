const express = require('express');
const router = express.Router();

router.get('/', async (req, res) => {
  try {
    const { userId } = req.query;
    
    const shortcuts = [
      {
        id: '1',
        trigger: 'brb',
        expansion: 'be right back',
        userId: userId || 'default'
      },
      {
        id: '2',
        trigger: 'thx',
        expansion: 'thank you',
        userId: userId || 'default'
      },
      {
        id: '3',
        trigger: 'omw',
        expansion: 'on my way',
        userId: userId || 'default'
      },
      {
        id: '4',
        trigger: 'ttyl',
        expansion: 'talk to you later',
        userId: userId || 'default'
      },
      {
        id: '5',
        trigger: 'np',
        expansion: 'no problem',
        userId: userId || 'default'
      }
    ];
    
    res.json(shortcuts);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/', async (req, res) => {
  try {
    const shortcut = req.body;
    shortcut.id = Date.now().toString();
    res.json(shortcut);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.delete('/:id', async (req, res) => {
  try {
    res.json({ success: true });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
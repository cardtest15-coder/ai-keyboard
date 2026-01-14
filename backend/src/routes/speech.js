const express = require('express');
const router = express.Router();

router.post('/recognize', async (req, res) => {
  try {
    const { audioData, language } = req.body;
    
    res.json({
      text: "Speech recognition result",
      confidence: 0.95,
      language: language || 'en'
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/languages', (req, res) => {
  const languages = [
    { code: 'en', name: 'English' },
    { code: 'ru', name: 'Русский' },
    { code: 'es', name: 'Español' },
    { code: 'de', name: 'Deutsch' },
    { code: 'fr', name: 'Français' },
    { code: 'zh', name: '中文' },
    { code: 'ja', name: '日本語' }
  ];
  
  res.json(languages);
});

module.exports = router;
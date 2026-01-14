const express = require('express');
const router = express.Router();
const translationService = require('../services/translationService');

router.post('/translate', async (req, res) => {
  try {
    const { text, sourceLang, targetLang } = req.body;
    
    if (!sourceLang) {
      sourceLang = await translationService.detectLanguage(text);
    }
    
    const result = await translationService.translateText(text, sourceLang, targetLang);
    res.json(result);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/detect', async (req, res) => {
  try {
    const { text } = req.query;
    const language = await translationService.detectLanguage(text);
    res.json({ language });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
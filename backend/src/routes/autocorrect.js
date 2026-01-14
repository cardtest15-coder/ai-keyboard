const express = require('express');
const router = express.Router();
const AutocorrectService = require('../services/autocorrectService');

router.post('/correct', async (req, res) => {
  try {
    const { text, language } = req.body;
    const result = await AutocorrectService.correctText(text, language);
    res.json(result);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/grammar-check', async (req, res) => {
  try {
    const { text, language } = req.body;
    const suggestions = await AutocorrectService.checkGrammar(text, language);
    res.json(suggestions);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
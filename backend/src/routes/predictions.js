const express = require('express');
const router = express.Router();
const PredictionService = require('../services/predictionService');

router.get('/predict', async (req, res) => {
  try {
    const { text, language } = req.query;
    const predictions = await PredictionService.getPredictions(text, language);
    res.json(predictions);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/next-word', async (req, res) => {
  try {
    const { text, language } = req.query;
    const predictions = await PredictionService.getNextWordPrediction(text, language);
    res.json(predictions);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.get('/sentence-complete', async (req, res) => {
  try {
    const { text, language } = req.query;
    const completions = await PredictionService.getSentenceCompletion(text, language);
    res.json(completions);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
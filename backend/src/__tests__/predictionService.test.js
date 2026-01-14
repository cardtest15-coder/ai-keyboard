const PredictionService = require('../services/predictionService');

describe('PredictionService', () => {
  describe('getPredictions', () => {
    it('should return predictions for given text', async () => {
      const predictions = await PredictionService.getPredictions('hel', 'en');
      expect(predictions).toBeDefined();
      expect(Array.isArray(predictions)).toBe(true);
      expect(predictions.length).toBeGreaterThan(0);
    });

    it('should handle empty text', async () => {
      const predictions = await PredictionService.getPredictions('', 'en');
      expect(predictions).toBeDefined();
    });

    it('should support multiple languages', async () => {
      const enPredictions = await PredictionService.getPredictions('привет', 'ru');
      expect(enPredictions).toBeDefined();
    });
  });

  describe('getNextWordPrediction', () => {
    it('should return next word predictions', async () => {
      const predictions = await PredictionService.getNextWordPrediction('hello', 'en');
      expect(predictions).toBeDefined();
      expect(Array.isArray(predictions)).toBe(true);
    });
  });
});
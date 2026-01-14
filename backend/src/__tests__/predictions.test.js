const request = require('supertest');
const express = require('express');
const predictionsRoutes = require('../routes/predictions');

const app = express();
app.use('/api/predictions', predictionsRoutes);

describe('Predictions Routes', () => {
  describe('GET /api/predictions/predict', () => {
    it('should return predictions for text', async () => {
      const response = await request(app)
        .get('/api/predictions/predict?text=hello&language=en');

      expect(response.statusCode).toBe(200);
      expect(Array.isArray(response.body)).toBe(true);
    });
  });

  describe('GET /api/predictions/next-word', () => {
    it('should return next word predictions', async () => {
      const response = await request(app)
        .get('/api/predictions/next-word?text=I think&language=en');

      expect(response.statusCode).toBe(200);
      expect(Array.isArray(response.body)).toBe(true);
    });
  });
});
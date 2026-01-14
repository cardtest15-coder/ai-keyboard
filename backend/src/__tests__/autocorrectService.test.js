const AutocorrectService = require('../services/autocorrectService');

describe('AutocorrectService', () => {
  describe('correctText', () => {
    it('should correct common misspellings', async () => {
      const result = await AutocorrectService.correctText('teh quick fox', 'en');
      expect(result.corrected).toContain('the');
      expect(result.changes).toBeGreaterThan(0);
    });

    it('should handle correctly spelled text', async () => {
      const result = await AutocorrectService.correctText('the quick fox', 'en');
      expect(result.changes).toBe(0);
    });

    it('should preserve punctuation', async () => {
      const result = await AutocorrectService.correctText('teh,', 'en');
      expect(result.corrected).toContain(',');
    });
  });

  describe('checkGrammar', () => {
    it('should suggest capitalization for lowercase sentences', async () => {
      const suggestions = await AutocorrectService.checkGrammar('hello world');
      expect(suggestions.length).toBeGreaterThan(0);
      expect(suggestions[0].type).toBe('capitalization');
    });

    it('should suggest punctuation for unpunctuated sentences', async () => {
      const suggestions = await AutocorrectService.checkGrammar('Hello');
      expect(suggestions.length).toBeGreaterThan(0);
    });
  });
});
const openai = require('openai');
require('dotenv').config();

class TranslationService {
  constructor() {
    this.client = new openai({
      apiKey: process.env.OPENAI_API_KEY
    });
  }

  async translateText(text, sourceLang, targetLang) {
    try {
      const response = await this.client.chat.completions.create({
        model: "gpt-3.5-turbo",
        messages: [
          {
            role: "system",
            content: `You are a translator. Translate the following text from ${sourceLang} to ${targetLang}.`
          },
          {
            role: "user",
            content: text
          }
        ],
        max_tokens: 500,
        temperature: 0.3
      });

      return {
        original: text,
        translated: response.choices[0].message.content.trim(),
        sourceLanguage: sourceLang,
        targetLanguage: targetLang
      };
    } catch (error) {
      console.error('Translation error:', error);
      return {
        original: text,
        translated: text,
        error: 'Translation failed'
      };
    }
  }

  async detectLanguage(text) {
    const languages = {
      'en': ['the', 'is', 'and', 'to', 'of', 'a'],
      'ru': ['и', 'в', 'не', 'что', 'я', 'на'],
      'es': ['el', 'la', 'de', 'que', 'y', 'a'],
      'de': ['der', 'die', 'und', 'in', 'den', 'von']
    };

    let maxMatches = 0;
    let detectedLang = 'en';

    for (const [lang, commonWords] of Object.entries(languages)) {
      const matches = commonWords.filter(word => 
        text.toLowerCase().includes(word)
      ).length;
      
      if (matches > maxMatches) {
        maxMatches = matches;
        detectedLang = lang;
      }
    }

    return detectedLang;
  }
}

module.exports = new TranslationService();
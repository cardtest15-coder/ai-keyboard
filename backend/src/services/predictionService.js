class PredictionService {
  static async getPredictions(text, language = 'en') {
    const predictions = [];
    const lastWords = text.split(' ').slice(-3).join(' ');
    
    const commonPatterns = {
      'en': ['hello', 'thank you', 'please', 'sorry', 'how are you', 'I think', 'I want', 'can you', 'would you'],
      'ru': ['привет', 'спасибо', 'пожалуйста', 'извините', 'как дела', 'я думаю', 'я хочу', 'можете', 'хотите'],
      'es': ['hola', 'gracias', 'por favor', 'lo siento', 'cómo estás', 'creo que', 'quiero', 'puedes', 'gustaría'],
      'de': ['hallo', 'danke', 'bitte', 'entschuldigung', 'wie geht es', 'ich denke', 'ich möchte', 'können Sie', 'würden Sie']
    };
    
    const phrases = commonPatterns[language] || commonPatterns['en'];
    
    phrases.forEach(phrase => {
      if (phrase.toLowerCase().startsWith(lastWords.toLowerCase()) || lastWords.length < 2) {
        predictions.push({
          text: phrase,
          confidence: Math.random() * 0.3 + 0.7
        });
      }
    });
    
    predictions.sort((a, b) => b.confidence - a.confidence);
    return predictions.slice(0, 5);
  }
  
  static async getNextWordPrediction(text, language = 'en') {
    const words = text.split(' ');
    const lastWord = words[words.length - 1];
    
    const nextWords = await this.getPredictions(lastWord, language);
    return nextWords.map(p => p.text);
  }
  
  static async getSentenceCompletion(text, language = 'en') {
    const completions = await this.getPredictions(text, language);
    return completions.map(c => c.text);
  }
}

module.exports = PredictionService;
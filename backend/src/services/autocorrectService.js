class AutocorrectService {
  static commonMisspellings = {
    'en': {
      'teh': 'the',
      'recieve': 'receive',
      'occured': 'occurred',
      'seperate': 'separate',
      'definately': 'definitely',
      'wether': 'whether',
      'untill': 'until',
      'begining': 'beginning',
      'goverment': 'government',
      'enviroment': 'environment'
    },
    'ru': {
      'пришол': 'пришел',
      'счас': 'щас',
      'чтобы': 'чтоб',
      'такжэ': 'также',
      'больше': 'более',
      'меньше': 'менее',
      'очень': 'совсем',
      'потому': 'потому что',
      'чот': 'что-то'
    }
  };

  static async correctText(text, language = 'en') {
    const words = text.split(' ');
    const correctedWords = [];
    
    const misspellings = this.commonMisspellings[language] || this.commonMisspellings['en'];
    
    for (let word of words) {
      const cleanWord = word.toLowerCase().replace(/[^\wа-яё]/g, '');
      const punctuation = word.replace(/[^\wа-яё]/g, '').length > 0 ? 
        word.slice(word.replace(/[^\wа-яё]/g, '').length) : '';
      
      const correction = misspellings[cleanWord] || null;
      
      if (correction && cleanWord !== correction) {
        correctedWords.push(correction + punctuation);
      } else {
        correctedWords.push(word);
      }
    }
    
    return {
      original: text,
      corrected: correctedWords.join(' '),
      changes: words.filter((w, i) => w !== correctedWords[i]).length
    };
  }
  
  static async checkGrammar(text, language = 'en') {
    const suggestions = [];
    
    if (text.length > 0 && text[0] !== text[0].toUpperCase()) {
      suggestions.push({
        type: 'capitalization',
        message: 'Consider capitalizing the first letter',
        suggestion: text.charAt(0).toUpperCase() + text.slice(1)
      });
    }
    
    if (!text.endsWith('.') && !text.endsWith('!') && !text.endsWith('?')) {
      suggestions.push({
        type: 'punctuation',
        message: 'Consider adding punctuation at the end',
        suggestion: text + '.'
      });
    }
    
    return suggestions;
  }
}

module.exports = AutocorrectService;
export interface Prediction {
  text: string;
  confidence: number;
}

export interface AutocorrectResult {
  original: string;
  corrected: string;
  changes: number;
}

export interface TranslationResult {
  original: string;
  translated: string;
  sourceLanguage: string;
  targetLanguage: string;
  error?: string;
}

export interface Shortcut {
  id: string;
  trigger: string;
  expansion: string;
  userId?: string;
}

export interface Template {
  id?: string;
  userId: string;
  name: string;
  content: string;
  tags: string[];
  usageCount: number;
  isPublic: boolean;
  createdAt?: Date;
}

export interface UserPreferences {
  language: string;
  theme: string;
  hapticFeedback: boolean;
  soundEnabled: boolean;
  autoCorrectEnabled: boolean;
  predictiveTextEnabled: boolean;
}

export interface TypingStats {
  wordsTyped: number;
  charactersTyped: number;
  avgWPM: number;
  accuracy: number;
}

export interface GestureType {
  type: string;
  description: string;
}

export interface TypingHistory {
  userId: string;
  text: string;
  context?: string;
  app?: string;
  timestamp: Date;
  wpm?: number;
  corrections?: Array<{
    original: string;
    corrected: string;
  }>;
}
const express = require('express');
const router = express.Router();
const jwt = require('jsonwebtoken');
const User = require('../models/User');

router.post('/register', async (req, res) => {
  try {
    const { email, password, preferences } = req.body;
    
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      return res.status(400).json({ error: 'User already exists' });
    }
    
    const user = new User({
      userId: Math.random().toString(36).substr(2, 9),
      email,
      password,
      preferences: preferences || {}
    });
    
    await user.save();
    
    const token = jwt.sign({ userId: user.userId }, process.env.JWT_SECRET || 'secret', {
      expiresIn: '30d'
    });
    
    res.json({ token, userId: user.userId });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

router.post('/login', async (req, res) => {
  try {
    const { email, password } = req.body;
    
    const user = await User.findOne({ email });
    if (!user || user.password !== password) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }
    
    const token = jwt.sign({ userId: user.userId }, process.env.JWT_SECRET || 'secret', {
      expiresIn: '30d'
    });
    
    res.json({ token, userId: user.userId });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;
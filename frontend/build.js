const fs = require('fs');
const path = require('path');

const appJsPath = path.join(__dirname, 'app.js');
let appJsContent = fs.readFileSync(appJsPath, 'utf8');

const apiUrl = process.env.API_URL || 'https://hms-backend-yq05.onrender.com';
console.log(`Injecting API_URL: ${apiUrl}`);

// Replaces the DEFAULT_API URL definition in app.js
appJsContent = appJsContent.replace(
  /const DEFAULT_API = isLocal \? "[^"]+" : "[^"]+";/,
  `const DEFAULT_API = isLocal ? "http://localhost:8080" : "${apiUrl}";`
);

fs.writeFileSync(appJsPath, appJsContent, 'utf8');

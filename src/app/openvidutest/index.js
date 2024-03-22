import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './page';
import registerServiceWorker from './registerServiceWorker';
import { createRoot } from 'react-dom/client'

createRoot(<App />, document.getElementById('root'));
registerServiceWorker();

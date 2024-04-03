importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.0.2/firebase-messaging.js');

const config = {
  apiKey: "AIzaSyDVjW-x8C0eQxaz9TT_atctuIzLQrVo3-c",
  authDomain: "matchup-c9f78.firebaseapp.com",
  projectId: "matchup-c9f78",
  storageBucket: "matchup-c9f78.appspot.com",
  messagingSenderId: "109793865216",
  appId: "1:109793865216:web:f9f19fff1628756073f2e5",
  measurementId: "G-NG2181NBLP"
};

firebase.initializeApp(config);
const messaging = firebase.messaging();

messaging.setBackgroundMessageHandler((payload) => {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  
  const notificationTitle = payload.data.name;
  const notificationOptions = {
    body: payload.data.content,
    icon: payload.data.iconUrl
  };

  return self.registration.showNotification(notificationTitle, notificationOptions);
});

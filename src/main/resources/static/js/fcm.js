firebase.initializeApp({
    apiKey: "AIzaSyAVOtxwefoChl7Yz03ZhdqiS2rOgvAFVxs",
    authDomain: "test-notifi-vnoip.firebaseapp.com",
    projectId: "test-notifi-vnoip",
    storageBucket: "test-notifi-vnoip.appspot.com",
    messagingSenderId: "114523000139",
    appId: "1:114523000139:web:ce1381ec2a72c704c96cce",
    measurementId: "G-K2JDH1M5Q9"
});
const messaging = firebase.messaging();
messaging.usePublicVapidKey('BNlOZiqaaWEJ-l1-u91Foru0mJfKQmhIC2EVx0uhekvyT5rb-HuMSaolCEQ1HvWDsfdpCbgeCddCq4Yd9KZyT9o');

genFCMCookie();
let FCMCookie = getFCMCookie("FCMCookie");

let currentToken;
messaging
    .requestPermission()
    .then(function () {
        console.log('Notification permission granted.');
        // get the token in the form of promise
        return messaging.getToken();
    })
    .then(function (token) {
        console.log(token);
        currentToken = token;

        fetch("/firebase-messaging/register", {
            method: 'post',
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            },
            body: JSON.stringify({
                firebaseToken: currentToken,
                deviceId: FCMCookie,
            })
        })
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });

    })
    .catch(function (err) {
        console.log('Unable to get permission to notify.', err);
    });

messaging.onTokenRefresh(function() {
    console.log('token refreshed');
    const newToken = messaging.getToken();
    console.log(newToken);
    // $.ajax({
    //     type: "POST",
    //     url: "/firebase-messaging/register",
    //     contentType: "application/json; charset=utf-8",
    //     data: JSON.stringify({
    //         firebaseToken: newToken,
    //         deviceId: FCMCookie,
    //     }),
    // });
});
messaging.onMessage(function (payload) {
    console.log('Message received. ', payload);

    navigator.serviceWorker
        .getRegistrations()
        .then((registration) => {
            registration[0].showNotification(
                payload.data.title,
                {
                    body: payload.data.content,
                    data: {
                        messageId : payload.data.messageId,
                        targetId : payload.data.targetId
                    },
                    icon : 'https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg'
                });
        });
    console.log('Data ', payload.data);
});

function getFCMCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

function setFCMCookie(name,value,days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days*24*60*60*1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}
function randFCM() {
    return Math.random().toString(36).substr(2); // remove `0.`
};

function tokenFCM() {
    return randFCM() + randFCM(); // to make it longer
};

function genFCMCookie(){
    var cookie = getFCMCookie("FCMCookie");

    if (cookie == null) {
        setFCMCookie("FCMCookie", tokenFCM(), 60);
    }
}

$("form[action='/logout']").click(function(){
    messaging
        .requestPermission()
        .then(function () {
            console.log('Notification permission granted.');
            // get the token in the form of promise
            return messaging.getToken();
        })
        .then(function (token) {
            return messaging.deleteToken(token);
        })
        .catch(function (err) {
            console.log('Unable to get permission to notify.', err);
        });
});
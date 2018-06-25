// Load the required libraries
let Pusher = require('pusher');
let express = require('express');
let bodyParser = require('body-parser');
const PushNotifications = require('@pusher/push-notifications-server');


// initialize express and pusher and pusher beams
let app = express();
let pusher = new Pusher(require('./config.js'));
let pushNotifications = new PushNotifications(require('./config.js'))

// Middlewares
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));

app.get('/posts', (req, res) => {

  var arrayEvents = ["Russia (4-2-3-1): Igor Akinfeev; Mario Fernandes, Ilya Kutepov, Sergey Ignashevich, Yury Zhirkov; Yuri Gazinskiy, Roman Zobnin; Aleksandr Samedov, Alan Dzagoev, Aleksandr Golovin; Fedor Smolov.",
  "Finally, the festival of football is here. We've got 64 games, 32 teams...but there can be only one winner. And the action starts today!!  ",
  "Hello and welcome to live text commentary of the Group A match between Russia and Saudi Arabia at the 2018 World Cup in Russia. The scene is set for the tournament opener!"];

  var arrayTime = ["15'", "10'", "5'"];

  
  let sendPushNotification = () => {
    var currentPost = arrayEvents.pop()
    var currentTime = arrayTime.pop()

    pushNotifications.publish(
      ['world-cup'],{
      fcm: {
        notification: {
          title: 'New post',
          body: currentPost
        }
      }
    }).then((publishResponse) => {
      console.log('Just published:', publishResponse.publishId);
    });

    pusher.trigger('soccer', 'world-cup', {currentTime, currentPost});
  }

  sendPushNotification()

  let sendToPusher = setInterval(() => {
    sendPushNotification()

    if (arrayEvents.length == 0) {
      clearInterval(sendToPusher)
    }
  }, 5000);

  res.json({success: 200})
});


// index
app.get('/', (req, res) => res.json("It works!"));

// serve app
app.listen(4000, _ => console.log('App listening on port 4000!'));

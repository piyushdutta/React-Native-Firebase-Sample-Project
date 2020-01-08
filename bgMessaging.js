// import firebase from '@react-native-firebase/messaging';
// // Optional flow type
// import type { RemoteMessage } from '@react-native-firebase/messaging';

// export default async (message: RemoteMessage) => {
//     // handle your message
    
//       console.log('FCM Message Data background:', RemoteMessage.data);
//    //    CleverTap_command.createNotificationChannel("channelID","channelName","channelDescription",3,true);
//    //    CleverTap_command.createNotification(RemoteMessage.data);
//    // // });

// 	return Promise.resolve();
 
// }


// setBackgroundMessageHandler(
//   handler: (message: RemoteMessage) => any
// ):any;

import firebase from '@react-native-firebase/messaging';
// Optional flow type
// import type { RemoteMessage } from 'react-native-firebase';

export default async (RemoteMessage: RemoteMessage) => {
    // handle your message
    const CleverTap_command = require('clevertap-react-native');
    console.log('FCM Message Data background:', RemoteMessage.data);
    CleverTap_command.createNotificationChannel("channelID","channelName","channelDescription",3,true);
    CleverTap_command.createNotification(RemoteMessage.data);
    return Promise.resolve();
}
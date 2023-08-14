self.addEventListener("install", function (e) {
  console.log("fcm sw install..");
  self.skipWaiting();
});

self.addEventListener("activate", function (e) {
  console.log("fcm sw activate..");
});

self.addEventListener("push", function (e) {
  console.log("push: ", e.data.json());
  if (!e.data.json()) return;

  const resultData = e.data.json().notification;
  const notificationTitle = "긴급 상황 발생";
  const notificationOptions = {
    body: "버스 기사님이 화상 통화를 요청하셨습니다.",
    icon: "bus_512.png",
    image: "alarm.png",
    tag: resultData.tag,
    ...resultData,
  };
  console.log("push: ", { resultData, notificationTitle, notificationOptions });

  self.registration.showNotification(notificationTitle, notificationOptions);
});

self.addEventListener("notificationclick", function (event) {
  console.log("notification click");
  const url = "/emergency";
  event.notification.close();
  event.waitUntil(clients.openWindow(url));
});

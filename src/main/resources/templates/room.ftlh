<!doctype html>
<html lang="en">
<head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<div class="container" id="app" v-cloak>
    <div class="row">
        <div class="col-md-12">
            <h3>채팅방 리스트</h3>
        </div>
    </div>
    <div class="input-group">
        <div class="input-group-prepend">
            <label class="input-group-text">소환사명</label>
        </div>
        <input type="text" class="form-control" v-model="summoner_name" @keyup.enter="createRoom">
        <div class="input-group-append">
            <button class="btn btn-primary" type="button" @click="createRoom">채팅방 개설</button>
        </div>
    </div>
    <ul class="list-group">
        <li class="list-group-item list-group-item-action" v-for="item in chatrooms" v-bind:key="item.roomId"
            v-on:click="enterRoom(item.roomId)">
            {{item.roomId}}
        </li>
    </ul>
</div>
<!-- JavaScript -->
<script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
<script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
<script src="/webjars/bootstrap/4.3.1/dist/js/bootstrap.min.js"></script>
<script src="/webjars/sockjs-client/1.1.2/sockjs.min.js"></script>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            summoner_name: '',
            chatrooms: []
        },
        created() {
            this.findAllRoom();
        },
        methods: {
            findAllRoom: function () {
                axios.get('/comm/rooms').then(response => {this.chatrooms = response.data;});
            },
            createRoom: function () {
                if ("" === this.summoner_name) {
                    alert("소환사 명을 입력.");

                } else {
                    var params = new URLSearchParams();
                    params.append("summonerName", this.summoner_name);
                    axios.post('/comm/room', params)
                        .then(
                            response => {
                                alert(response.data.roomId + "방 개설에 성공하였습니다.")
                                this.summoner_name = '';
                                this.findAllRoom();
                            }
                        )
                        .catch(response => {
                            alert("채팅방 개설에 실패하였습니다.");
                        });
                }
            },
            enterRoom: function (roomId) {
                localStorage.setItem('wschat.roomId', roomId);
                location.href = "/comm/room/enter/" + roomId;
            }
        }
    });
</script>
</body>
</html>
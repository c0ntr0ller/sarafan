
function getIndex(list, id){
    for (var i = 0; i < list.length; i++){
        if(list[i].id === id){
            return i;
        }
    }

    return -1;
}

var messageApi = Vue.resource('/message{/id}');

Vue.component('message-form',{
    props: ['messages', 'messageAttr'],
    data: function () {
        return {
            formtext:'',
            id: ''
        }
    },
    watch: {
        messageAttr: function(newVal, oldVal) {
            if(newVal) {
                this.formtext = newVal.text;
                this.id = newVal.id;
            }
        }
    },
    template:
    '<div>' +
        '<input type="text" placeholder="Type something" v-model="formtext" />' +
        '<input type="button" value="Save" v-on:click="save"/>' +
    '</div>',
    methods:{
        save: function(){
            var message = {text: this.formtext};

            if(this.id){
                messageApi.update({id: this.id}, message).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.messages, data.id);

                        this.messages.splice(index, 1, data);
                    })
                )
            }else {
                messageApi.save({}, message).then(result =>
                    result.json().then(data => {
                        this.messages.push(data);

                    })
                )
            };
            this.formtext = '';
            this.id = '';
        }
    }
});

Vue.component('message-row',{
    props: ['message', 'messages', 'editMethod', 'deleteMethod'],
    template:
    '<div>' +
        '<i>({{ message.id }} )</i>' +
        '{{message.text}}' +
        '<span>' +
            '<input type="button" value="Edit" @click="editMessageRow" />' +
            '<input type="button" value="X" @click="deleteMessageRow" />' +
        '</span>' +
    '</div>',
    methods:{
        editMessageRow: function () {
            this.editMethod(this.message)
        },
        deleteMessageRow: function () {
            messageApi.remove({id:this.message.id}).then(result =>{
                if(result.ok){
                    this.messages.splice(this.messages.indexOf(this.message), 1)
                }
            })
        }
    }
});

Vue.component('messages-list',{
    props: ['messages'],
    data: function () {
        return{
            message:null
        }
    },
    template:
        '<div>' +
            '<message-form :messages="messages" :messageAttr="message"/>' +
            '<message-row v-for="message in messages" :key="message.id" :message="message" :messages="messages" :editMethod="editMethod"/>' +
        '</div>',
    methods: {
        editMethod: function (message) {
            this.message = message;
        }
    }
});

var app = new Vue({
    el: '#app',
    template:'<div>' +
    '<messages-list :messages="messages" />' +
    '<div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>' +
    '</div>',
    data: {
        messages: frontendData.messages,
        profile: frontendData.profile
    },
    created: function() {
        // messageApi.get().then(result =>
        // result.json().then(data =>
        // data.forEach(message => this.messages.push(message))
        // ))
    }
});
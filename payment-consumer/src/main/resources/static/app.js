let stompClient = null

function connectToWebSocket() {
    const socket = new SockJS('/payment-websocket')
    stompClient = Stomp.over(socket)

    stompClient.connect({},
        function (frame) {
            console.log('Connected: ' + frame)
            $('.connWebSocket').find('i').removeClass('red').addClass('green')

            stompClient.subscribe('/topic/payments', function (paymentEvent) {
                const paymentEventBody = JSON.parse(paymentEvent.body)
                const payment = paymentEventBody.payment
                const action = paymentEventBody.action

                const $payment = $('#' + payment.id)
                if (action === 'REMOVE' && $payment.length !== 0) {
                    $payment.transition({
                        animation: 'flash',
                        onComplete: function() {
                          $payment.remove()
                        }
                      }
                    )
                } else if (action === 'INSERT' && $payment.length === 0) {
                                    const paymentItem = '<div class="item" id="'+$payment.id+'">' +
                                                       '<div class="content">' +
                                                         '<div class="meta">' +
                                                           '<span>'+moment($payment.publishedAt).format("DD-MMM-YYYY HH:mm:ss")+'</span>' +
                                                         '</div>' +
                                                         '<div class="ui divider"></div>' +
                                                         '<div class="ui big header">'+$payment.customerId+'</div>' +
                                                         '<div class="ui divider"></div>' +
                                                         '<div class="ui big header">'+$payment.amount+'</div>' +
                                                       '</div>' +
                                                     '</div>'
                                    $('#paymentsList').prepend(paymentItem)
                                    $('#' + $payment.id).transition('glow')
                                }
                            })
                        },
        function() {
            showModal($('.modal.alert'), 'WebSocket Disconnected', 'WebSocket is disconnected. Maybe, payment-consumer is down or restarting')
            $('.connWebSocket').find('i').removeClass('green').addClass('red')
        }
     )
}

function showModal($modal, header, description, fnApprove) {
    $modal.find('.header').text(header)
    $modal.find('.content').text(description)
    $modal.modal('show')
}

$(function () {
    connectToWebSocket()

    $('.connWebSocket').click(function() {
        connectToWebSocket()
    })
})

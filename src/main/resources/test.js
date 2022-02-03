const crypto = require('crypto')
const request = require('request')

const apiKey = 'j7QuNBeox5Dx0vasg2ppOWXo4HW3x7x8xiOP2dkp';
const apiSecret = '1jlYPvTJowRg1BV4XfDrZZVSVHE28wU0Q2IEonO2';

const apiPath = '/v3/auth/merchant/payment_services'
const nonce = Date.now().toString()
const body = {
    "currency": "uah",
    "amount": 100,
    "payment_service": "default",
    "return_url": "https://your.site.url",
    "callback_url": "https://callback.url"
}

let signature = `${apiPath}${nonce}${JSON.stringify(body)}`

const hmac = crypto.createHmac('sha384', apiSecret)
const sig = hmac.update(signature)
const shex = sig.digest('hex')
const options = {
    url: `https://api.kuna.io${apiPath}`,
    headers: {
        'accept': 'application/json',
        'content-type': 'application/json',
        'kun-nonce': nonce,
        'kun-apikey': apiKey,
        'kun-signature': shex
    },
    body: body,
    json: true
}

console.log(shex);

request.post(options, (error, response, body) => {
    console.log(body);
})




// NOTE:
// This file is the reference implementation that this project is replicating
// The result of running this file can be found in mkeas-out.txt
// Originally by Matt Keas: https://goo.gl/NPaSi4

let channel = () => {
    let c = [],
        channel_closed = false,
        actors = [],
        without = (c, name, val) => c.filter((a) => a[name] !== val),
        each = (c, fn) => c.forEach(fn)

    const put = (val) => {
            if(typeof val === 'undefined') return ["park", null]
            c.unshift(val)
            return ["continue", null]
        },
        take = () => {
            let val = c.pop()
            return [ val !== undefined ? 'continue' : 'park', val ]
        },
        spawn = function(gen){
            let iter = gen(put, take)

            let actor = () => {
                let step = iter.next()
                if(step.done || channel_closed) return
                let [state, value] = step.value || ['park']
                if(state === 'park'){
                    setTimeout(() => each(without(actors, 'id', actor.id), (a) => a() ))
                } else if(state === "continue"){
                    setTimeout(actor)
                }
            }

            actor.id = Math.random()
            actors.push(actor)

            actor()
        }

    return {
        spawn,
        close: () => {
            channel_closed = true
        }
    }
}

/**
API

channel.spawn()
channel.close()
**/


let x = channel() // create new channel()

reset() // clear the right hand side

// for any value in the channel, pull it and log it
x.spawn( function* (put, take) {
    while(true){
        let val = take()
        yield val
        if(val[1]) log(`-------------------taking: ${val[1]}`)
    }
})

// put each item in fibonnaci sequence, one at a time
x.spawn( function* (put, take) {
    let [x, y] = [0, 1],
        next = x+y

    for(var i = 0; i < 100; i++) {
        next = x+y
        log(`putting: ${next}`)
        yield put(next)
        yield // pause!
        x = y
        y = next
    }
})

// immediately, and every .5 seconds, put the date/time into channel
function* insertDate(p, t) { yield p(new Date); yield p() }
setInterval(() => x.spawn(insertDate), 500)
x.spawn(insertDate)

// close the channel and remove all memory references. Pow! one-line cleanup.
setTimeout(() => x.close(), 4000)

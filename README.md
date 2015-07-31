# core.async demo

[Matt Keas] posted a neat [demo] of a [CSP]-style example using new JavaScript
language features available in ES6.

I talked a [big game] on Twitter about how I thought something similar could be
accomplished using CLJS in less lines of code with an easy-to-understand
implementation.

This quick project is to see whether my big talk was justified or not ;)

## Development

Install [Leiningen] and [Node.js].

```sh
# build the ClojureScript file
lein cljsbuild once

# run the compiled file
node app.js

```

## License

ISC License

[Matt Keas]:https://twitter.com/matthiasak
[demo]:https://goo.gl/NPaSi4
[CSP]:https://en.wikipedia.org/wiki/Communicating_sequential_processes
[big game]:https://twitter.com/matthiasak/status/623895714058272773
[Leiningen]:http://leiningen.org/
[Node.js]:https://nodejs.org/

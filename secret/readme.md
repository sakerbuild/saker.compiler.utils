# saker.compiler.utils secrets

This directory contains maintainer-private details for the saker.compiler.utils package.

In order to upload the exported bundles to the saker.nest repository, there must be a `secrets.build` file with the following contents:

```
global(saker.compiler.utils.UPLOAD_API_KEY) = <api-key>
global(saker.compiler.utils.UPLOAD_API_SECRET) = <api-secret>
```

It is used by the `upload` target in `saker.build` build script.
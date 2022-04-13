# two-way-ssl-authorization

## SSL most common commands

#### Generate Private Key
`openssl genrsa -out private.key 4096`

#### Generate Public Key
`openssl rsa -in private.key -pubout -out public.key`

#### Generate CSR (Certificate Signing Request)
`openssl req -new -key private.key -out request.csr`

#### View CSR info
`openssl req -text -in request.csr`

#### Generate CRT (Certificate)
`openssl x509 -in request.csr -out cert.crt -req -signkey private.key -days 365`

#### Generate pkcs12 (.p12) full chain certificate
`openssl pkcs12 -export -out fullcert.p12 -inkey private.key -passout pass:123456 -in cert.crt`

#### Generate pkcs12 (.p12) full chain certificate with CA (Certificate Authority)
`openssl pkcs12 -export -out faberlic.p12 -inkey private.key -passout pass:123456 -in novage.crt -certfile CA.crt`

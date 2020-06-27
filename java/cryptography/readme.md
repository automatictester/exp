# Cryptography

### Ciphers

- Good cipher - brute force is the best possible attack
- Broken cipher - an attack substantially better than brute force exists

### Kerckhoff's Principle

- Cryptosystem should remain secure even if everything about the system, except the key, is public knowledge

### Trully Unbreakable Cryptographic System

- Only one such system exists:
  - One time key
  - Random key
  - len ( K ) >= len ( P ) 

### Block Ciphers

Types:
- Feistel network: early design, e.g. DES
- SP network, modern design, e.g. AES

### SP Network

- Multiple rounds of the same substitution and permutation
- R<sub>1</sub> = ( P ⊕ K<sub>1</sub> ), R<sub>2</sub> = ( P ⊕ K<sub>2</sub> ), ... , R<sub>n</sub> = C

### AES

- Previous name: Rijndael
- Type: SP network
- Block size: 128 bits (matrix 4x4 bytes)
- Key length / rounds: 128 bit with 10 rounds, 196 bit with 12 rounds, 256 bit with 14 rounds
- Operates on bytes, not bits
- With AES being a standard, Intel and AMD CPUs provide native support making it very fast
  (AES-NI - AES New Instructions)
- Used in Microsoft BitLocker and Apple FileVault 2 disc encryption

### Block Cipher Modes

|Mode|Encryption|Notes|
|---|---|---|
|ECB|C = E ( P )|
|CBC|C = E ( P ⊕ C<sub>i-1</sub> )|see <sup>1</sup>|
|CFB|C = E ( C<sub>i-1</sub> ) ⊕ P|see <sup>1</sup>|
|OFB|C<sub>0</sub> = E ( IV ) ⊕ P<br> C<sub>1</sub> = E ( E ( IV )<sub>0</sub> ) ⊕ P<br>...|see <sup>1</sup>|
|CTR|C = E ( N + C ) ⊕ P|uses nonce and counter|

<sup>1</sup> - in first iteration IV is used, as there is no C<sub>i-1</sub> yet

|Mode|Encryption parallelizable|Decryption parallelizable|Random access|
|---|---|---|---|
|ECB|yes|yes|yes|
|CBC|no|yes|yes|
|CFB|no|yes|yes|
|OFB|no|no|no|
|CTR|yes|yes|yes|

### Hash Functions

- Hashing is not encryption - reversing is not only not the goal, but should be impossible
- Should not be used to store masked passwords, due to ranbow table attacks

Insecure hash functions:
- CRC - non-cryptographic hash function
- MD5
- SHA-1

Secure hash functions:
- SHA-2
- SHA-3
- BLAKE

Digest size:
- MD5: 128 bit (32 hex)
- SHA1: 160 bit (40 hex)
- SHA256 - 256 bit (64 hex)

### SHA-1

- Block size: 512 bits
- Padding: `[1010101|1000...0000111]`, where:
  - `101010101` is actual message
  - followed by `1`
  - followed by number of `0`'s
  - followed by `111` defining actual length of actual data in this block

### Public Key Cryptography

- Slower, but more secure than symmetric cryptography. For this reason frequently used to establish a shared secret
  which is then used to encrypt actual communication
- Level of security < key length
- Key length < key size on disk
- In contrary to symmetric keys, not every combination of 2<sup>n</sup> bits results in a valid value which can be used 
  in public key cryptography
- Keys used in publik key cryptography are composed of a set of integers, not random sequence of bytes
- Encrypt with recipient's public key to guarantee only he can decrypt it
- Encrypt with your private key to prove authenticity

### RSA

Keys:
- Both public and private keys consist of a set of integers
- Private key is build of more integers than public key
- Most important of them, shared between public and private keys, is modulus
- Length of RSA key is defined by the bit length of its modulus

Key strength:
- 1024 bit RSA = 80 bit symmetric
- 2048 bit RSA = 112 bit symmetric
- 3072 bit RSA = 128 bit symmetric
- 15360 bit RSA = 256 bit symmetric
- 3072 bit RSA = 3072 bit DH

### Diffie Hellman Key Exchange

|Alice|Public|Bob|
|:---:|:---:|:---:|
|a|g, n|b|
|ag| |bg|
|ag, bg| |bg, ag|
|abg| |abg|

- requires RSA or another PK cryptography to avoid man-in-the-middle attacks

### Elliptic Curve Cryptography

Strength:
- 256 bit EC = 3072 bit RSA
- 384 bit EC = 7680 bit RSA - this is good enough for US Top Secret level

Benefits:
- Shorter key length
- Reduced storage and transmission requirements

### (Perfect) Forward Secrecy

- Compromising K<sub>x</sub> allows to decrypt messages encrypted using K<sub>x...n</sub>
  but doesn't allow to decrypt messages encrypted K<sub>0...x-1</sub> 
- DH key exchange - Forward Secrecy
- DHE key exchange (Ephemeral) - Perfect Forward Secrecy

# AsymmetricCryptography
Extension for Asymmetric Cryptography (For AI2 and its distros)

### Blocks
![image](https://user-images.githubusercontent.com/41724811/123958574-d7ad1d80-d9ca-11eb-8229-28f34142fb48.png) <br>
![image](https://user-images.githubusercontent.com/41724811/123958627-e562a300-d9ca-11eb-84a5-243c3e9c286a.png) <br>

### Documentation

>![image](https://user-images.githubusercontent.com/41724811/123958936-3ffbff00-d9cb-11eb-992c-3a4e1fa8151c.png) <br>
Generates keys of provided length and raises event 'KeysGenerated' with success and response values.<br>
Note: (i) Big key size will consume more ram.It behaves normal upto 4096.<br>
(ii) Key length should be at least 8 times  of string length.<br>
For Example :<br>
A key of length 1024 can encrypt a string of length 128.<br>

> ![image](https://user-images.githubusercontent.com/41724811/123958976-47bba380-d9cb-11eb-957f-730266026fa4.png)<br>
Returns private key in string format<br>

> ![image](https://user-images.githubusercontent.com/41724811/123959014-51450b80-d9cb-11eb-97e3-de9dfedc893c.png)<br>
Returns public key in string format<br>

> ![image](https://user-images.githubusercontent.com/41724811/123959230-8cdfd580-d9cb-11eb-8db4-e35ace825b53.png) <br>
Tries to encrypt given string with provided public key and returns encrypted text <br>

> ![image](https://user-images.githubusercontent.com/41724811/123959273-979a6a80-d9cb-11eb-8f2c-ae746c28cb3b.png) <br>
Tries to decrypt given string with provided private key and returns decrypted text <br>

> ![image](https://user-images.githubusercontent.com/41724811/123959302-a3862c80-d9cb-11eb-859b-110de124cb80.png) <br>
Asynchronous version of Decrypt method <br>

> ![image](https://user-images.githubusercontent.com/41724811/123959373-b862c000-d9cb-11eb-9fbf-991aeb78b9f4.png) <br>
Asynchronous version of Encrypt method <br>
### External References
**MIT App Inventor Community**: https://community.appinventor.mit.edu/t/asymmetriccryptography-an-extension-for-asymmetric-cryptography/7865 <br>
**Kodular Community**: https://community.kodular.io/t/asymmetriccryptography-an-extension-for-asymmetric-cryptography/60061

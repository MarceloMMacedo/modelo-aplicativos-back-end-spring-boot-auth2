# ModelRestRepository
model restrepository with oaut2

## Authors

- [@Marcelo M Macedo](https://github.com/MarceloMacedoDev/ModelRestRepository)


## Appendix

Modelo para start de aplicativos back-end em java Spring boot com auth2, DataRepository as camadas do controller e Services são criadas automaticamente


## Tech Stack

**Back-end:** Spring boot: data-jpa, data-postgressql, security-auth2, start-data-rest



## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`JWT_SECRET`

`JWT_DURATION`

`CLIENT-ID`

`CLIENT-SECRET`


## API Reference

#### Create barear token

```http
  POST /oauth/token
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `Username` | `string` | **Required**. Your Username of the App |
| `Password` | `string` | **Required**. Your Password of the App |
| `Body*username` | `string` | **Required**. Your Username of the User |
| `Body*password` | `string` | **Required**. Your Password of the User |
| `Body*grant_type` | `string` | **Required**. Your grant_type of the User |

 

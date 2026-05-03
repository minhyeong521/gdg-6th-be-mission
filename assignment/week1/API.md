# 소비자

## 1. 상품 조회
- HTTP Method: GET
- URL: /products

### request
상품명: "name"
#### json input
``` json:
{
    "name": "productA"
}
```
### response
- 상품ID: "Id"
- 상품명: "name"
- 가격: "price"
- 재고 수량: "stock"

#### json output
``` json: 
{
    "productId": 1,
    "name": "productA",
    "price": 50000,
    "stock": 10
}
```
- 200ok

## 2. 상품 구매
- HTTP Method: Post
- URL: /orders

### request
- 상품 ID: "productId"
- 구매 수량: "quantity"
#### json input
``` json
{
    {
        "productId": 1,
        "quantity": 3
    },
    {
        "productId": 2,
        "quantity": 5
    }  
}
```
### response
- 총 구매 금액: "totalPrice"
- 구매한 상품 목록 "ordersList"
- 201 created
#### json output
``` json
{
    "totalPrice": 650000,
    "ordersList": [
        {
            "name": "productA",
            "quantity": 3,
            "price": 150000
        },
        {
            "name": "productB",
            "quantity": 5,
            "price": 500000
        }
    ] 
}   
```

# 관리자

## 1. 상품 등록
- HTTP Method: Post
- URL: /products

### request
#### json input
``` json
{
    "name": "productC",
    "price": 200000,
    "stock": 5
}
```
### response
- 201 created
- 400 Bad Request(중복 입력 시 오류 발생)

## 2. 재고 추가
- HTTP Method: Patch
- URL: /products/{productId}/stock

### request 
- 추가할 수량: "stockPlus"
#### json input
``` json
{
    "stockPlus": 5
}
```

### response
- 200ok
#### json ouput
``` json
{
    "name": "productC",
    "stock": 10
}
```

## 3. 상품 삭제
- HTTP Method: Delete
- URL: /products

### request
- 삭제할 상품 ID 목록: "deleteList"
#### json input
``` json
{
    "deleteList": [1, 2]
}
```

### response
- 현재 남아 있는 상품 목록: "remainList"
#### json output
``` json
{
    "remainList": [
        {
            "name": "productC",
            "stock": 10
        }
    ]
}
```


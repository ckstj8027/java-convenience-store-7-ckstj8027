# 편의점 판매

### 기능

- [x]  할인혜택적용
- [x]  +one 혜택을 더 받을 수있는경우
- [Y] +one 혜택적용
- [N] 그대로 구매
- [x]  혜택을받아야함에도 재고상황에따라 못받을경우
- [Y] 일부혜택만받고 일부는 받지못함
- [N] 최대적용횟수만큼 구매

### 전략나누기

- [x]  프로모션적용
- [x]  프로모션있지만 비활성화상태
- [x]  일반

### 도메인

1. [x] **재고 관리**

- 재고 테이블을 만들어 각 상품의 재고 상태를 추적한다.
- 재고 수량, 상품 ID, 제품명, 가격 등을 포함한 정보 관리.
- 주문이 들어오면 재고에서 해당 수량을 차감하는 방식으로 관리.

2. [x] **주문 관리**

- 주문 테이블을 만들어 고객의 주문 정보를 기록한다.
- 상품 이름, 주문 수량

3. [x] **프로모션 관리**

- 프로모션 테이블을 만들어 활성화된 할인 혜택 및 프로모션 정보를 관리한다.
- 프로모션 이름,할인내용(n+1), 유효 기간 등을 관리.

4. [x] **상품 관리**

- 상품 테이블을 만들어 상품 정보를 관리한다.
- 이름, 가격, 프로모션 이름

5. [x] **재고 관리**

- 상품 테이블을 바탕으로 재고 테이블을 만들어 재고를 관리한다.
- 이름, 일반재고수량,프로모션판매수량

6. [x] **판매 관리**

- 판매 테이블을 만들어 프로모션판매수량과 일반판매수량 내역을 기록한다.

7. [x] **테이블 간 조인 및 관계 활용**

- 각 상품의 프로모션 적용 여부를 관리하여, 할인 혜택을 적용할 수 있도록 설정.
- 각 테이블(재고, 주문, 프로모션, 상품, 판매)은 서로 연관되어 있으며, 조인을 통해 필요한 데이터를 동적으로 활용한다.
- 예): **주문 테이블**과 **상품 테이블**을 조인하여 어떤 상품이 주문되었는지 파악.
- **프로모션 테이블**과 **주문 테이블**을 조인하여 프로모션이 적용된 주문 내역을 확인.
- **판매 테이블**과 **재고 테이블**을 조인하여 재고 차감을 자동화하고 판매 데이터를 기록.

### 진행순서

1. [x] 주문을 먼저 받아 주문 테이블을 만든다
2. [x] 전략 판단
    1. 일반 재고만 있을 경우
    2. 프로모션 적용될 경우
    3. 프로모션 비활성화된 경우
3. [x] 영수증 출력

    1. 각 전략에 따라 만든 판매 테이블을 순회하며 영수증 출력
4. [x] 재고 정리
    1. 각 전략에 따라 만든 판매 테이블을 바탕으로 재고 정리

### 각 전략에 맞추어 판매테이블을 만든다

- [x] 프로모션 활성화된경우
    - 추가 증정 상품이 필요한지 응답확인
    - 재고상황에따라 일부 혜택없이 구매 여부확인
- [x] 프로모션 비활성화된경우
    - 프로모션판매수량을 일반판매수량으로 전환 후
    - 프로모션판매수량은 0으로 만듬
- [x] 일반재고만 있을우
    - 프로모션판매수량은 0
    - 일반판매수량은 주문수량이된다

### 재고정리

- [x] 토탈판매수량 >프로모션 재고수량
    - 일반재고:초과된 판매량을 일반 재고에서 차감
    - 프로모션재고: 0으로정리
- [x] 토탈판매수량 <=프로모션 재고수량
    - 프로모션재고:프로모션재고수량 - 총판매수량
      -일반재고:그대로 유지

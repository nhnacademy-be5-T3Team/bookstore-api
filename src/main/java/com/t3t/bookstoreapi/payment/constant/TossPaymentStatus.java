package com.t3t.bookstoreapi.payment.constant;

/**
 * 토스 결제 상태를 나타내는 enum<br>
 * 각 상태 변화에 대한 흐름은 toss api 문서를 참고한다.
 * @author woody35545(구건모)
 * @see <a href="https://docs.tosspayments.com/reference#payment-%EA%B0%9D%EC%B2%B4">toss api `payment` object reference</a>
 */
public enum TossPaymentStatus {

    READY, // 결제를 생성하면 가지게 되는 초기 상태로 인증 전까지는 READY 상태를 유지한다.
    IN_PROGRESS, // 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태로 결제 승인 API를 호출하면 결제가 완료된다.
    WAITING_FOR_DEPOSIT, // 가상계좌 결제 흐름에만 있는 상태로 결제 고객이 발급된 가상계좌에 입금하는 것을 기다리고 있는 상태.
    DONE, // 인증된 결제수단 정보, 고객 정보로 요청한 결제가 승인된 상태.
    CANCELED, // 승인된 결제가 취소된 상태.
    PARTIAL_CANCELED, // 승인된 결제가 부분 취소된 상태.
    ABORTED, // 결제 승인이 실패한 상태.
    EXPIRED // 결제 유효 시간 30분이 지나 거래가 취소된 상태. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 된다.

}

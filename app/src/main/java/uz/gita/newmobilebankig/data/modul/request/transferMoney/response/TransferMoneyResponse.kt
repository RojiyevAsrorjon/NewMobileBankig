package uz.gita.newmobilebankig.data.modul.request.transferMoney.response

data class TransferMoneyResponse(
	val data: TransferMoneyData
)

data class TransferMoneyData(
	val amount: Int,
	val receiver: Int,
	val sender: Int,
	val fee: Int,
	val id: Int,
	val time: Long,
	val status: Int
)


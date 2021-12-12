package uz.gita.newmobilebankig.data.modul.request.transferMoney.request

data class 	TransferMoneyRequest(
	val amount: Int,
	val receiverPan: String,
	val sender: Int
)


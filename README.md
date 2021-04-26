 # terabytes

FIT3077 student-matching system

## To be refactored in order

1. `StudentDashboard` + `TutorDashboard` extends from `DashboardModel`
- `ContractPanel` (second column) is not aligned, need to provide fix-sized of column in the ContractPanel
- `ContractPanel` (label of Contract number) not stated yet
- Might need to remove "Select Offer" in `ContractPanel`
- `DashboardButtonPanel` "Contract" selection not updated, need to set contract dropdown based on size of Contract

2. `BiddingController`
- Fix `OpenBiddingView`, `CloseBiddingView` to be integrated into `DashboardController`
- Update `BiddingController` to listen to actions performed on `BiddingView` (Both OpenBid + CloseBid)

3. `OfferingView`
- Update `OfferingView` 

4. `ContractView`
- Update `ContractView`


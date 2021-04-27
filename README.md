 # terabytes

FIT3077 student-matching system

## To be refactored in order

We focus on top 2 first. 

<!-- 1. `StudentDashboard` + `TutorDashboard` extends from `DashboardModel`
- `ContractPanel` (second column) is not aligned, need to provide fix-sized of column in the ContractPanel
- `ContractPanel` (label of Contract number) not stated yet
- Might need to remove "Select Offer" in `ContractPanel`
- `DashboardButtonPanel` "Contract" selection not updated, need to set contract dropdown based on size of Contract -->

2. `BiddingController`
- Update `OpenBiddingView` to display bid infos rather than contracts
- Update `CloseBiddingView` to display bid infos rather than contracts
- Integrate both `OpenBiddingView` and `CloseBiddingView` `DashboardController`
- Update `BiddingController` to listen to actions performed on `BiddingView` (Both OpenBid + CloseBid)

3. `OfferingView`
- Update `OfferingView` 

4. `ContractView`
- Update `ContractView`

5. `StudentDashboard` + `TutorDashboard` extends from `DashboardModel`
- Smart resizing
- Scrollpane instead of jscrollpane to enable it to work for contracts of different sizes 
- update method resize panel
- user builder instead of inheritance

6. `Contract` 
- contain bid info? 
- 

# parking-lot-management System Design

## Models to be considerd
- Parking Slot
- Ticket
- Vehicle

## Operations to be considered
- Check availability of parking
- Park Vehicle
- Generate ticket after parking
- Vacate parking slot (unpark vehicle)
- Check current parking occupancy
- Vacate entire parking
- Generate parking charges based on time parked or weekday/weekend

## Points considered for design
- Parking is on single floor
- 2 Wheeler and 4 wheeler parking slots are fixed and must be configured in property file
- Ticket is generated once vehicle is parked
- Only 2 types of vehicles can be parked (2 wheeler and 4 wheeler)

## Pending Enhancement
- Web Request Controllers needs to be added to handle REST calls
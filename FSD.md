# Bonds Broker FSD

This is the Functional Specification Document of the Bonds Broker application
of a fictional Bonds4All.com company.


## Data model

### Entity: Bond

#### Attributes:

- Id (Auto-generated PK bigint)
- Name (mandatory string 100) - The client's name
- Start date (mandatory date) - Start date of the bond's term
- Amount (mandatory decimal 10 + 3)
- Reference (mandatory string 36)

### Entity: Bond term

#### Attributes:

- Id (Auto-generated PK bigint)
- Bond id (FK bigint)
- Effective time (mandatory timestamp)
- End date (mandatory date) - End date of the bond's term
- Interest rate (mandatory decimal 1 + 10)

### Entity: IP address request

#### Attributes:

- Effective date (mandatory date)
- IP (mandatory string 100)


## API

### Operation: Buy bond `POST /bonds`

#### Request:

- Name
- Start date
- End date
- Amount

#### Response:

- Reference

#### Main success scenario:

1. System validates the request.
2. System creates new Bond record.
3. System generates Bond Reference as random UUID.
4. System creates new Bond term record.
5. System sets Bond term Effective time to system current time.
6. System sets Bond term Interest rate to 0.05.
7. System creates new IP address request record.
8. System sets IP address request Effective date to current system date.
9. System sets IP address request IP to client IP address.
10. System returns the response.

#### Extensions:

- 1a. Start date < today:
    - 1a1. System rejects the request with error "Start date must not be in past".
- 1b. End date < Start date + 5 years:
    - 1b1. System rejects the request with error "Bond term length is too short".
- 1c. System local time is 2200-0600 and Amount > 1000:
    - 1c1. System rejects the request with error "Legal requirement violated: time and amount".
- 1d. Number of previous bonds bought on the current system local date from the same client IP address >= 5:
    - 1d1. System rejects the request with error "Legal requirement violated: too many bonds from IP address".

### Operation: Adjust bond `PATCH /bonds/{reference}`

#### Request:

- Reference
- End date

#### Main success scenario:

1. System validates the request.
2. System creates new Bond term record.
3. System sets Bond term Effective time to system current time.

#### Extensions:

- 1a. End date < Start date + 5 years:
    - 1a1. System rejects the request with error "Bond term must be at least 5 years".
- 3a. Term was extended (End date > End date on the last previous Bond term for the Bond):
    - 3a1. System sets Bond term Interest rate = Interest rate * 0.9.

### Operation: Get bond with history `GET /bonds/{reference}`

#### Request:

- Reference

#### Response:

- Name
- Start date
- Amount
- Terms: list of:
    - Effective time
    - End date
    - Interest rate

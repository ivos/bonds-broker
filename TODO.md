# TODO

- Verify FSD with Product owner
- Decimal attributes (amount, rate): rounding errors, precision?
- Expand personal data beyond Name
- IP address house-keeping (deleting old records)
- Switch IP address to cache
- Get correct IP address even behind proxy (HTTP headers?)
- Establish client's identity, then:
    - registration, authentication, authorization (see only my bonds)
    - list all bonds of the client
- Dates local to 1 zone (2200-0600, IP per "date"): multiple zones? Run in UTC & set local zone as property?
- Adjust bond with the same End date? Maybe do not create new Bond term?
- Switch to a production DB?
- Ensure uniqueness of the Bond Reference on the DB level
- Deal with empty strings (trim?, mixSize = 1?)
- Proper logging (aspect?)

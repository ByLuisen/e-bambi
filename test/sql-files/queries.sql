SELECT *
FROM offers
WHERE supplier_id = '0d95b4c4-a138-4e6a-9d86-4c27ea98a83c' AND product_id IN ('7435bce1-ff71-4f3e-b7d5-af16310637de',
'2e762dc5-40a0-477e-bf41-01aabfe1481e', '1c6e3e3c-7994-4b16-8b6d-3da13e2fb1f2', '1808388a-039e-49b6-944d-639f561d592b',
'4ccf4049-ebc3-4ef5-933c-1ab37abb4371', '511eefe6-ee90-4143-83b5-4331b5ebd0d0', '910bb12c-e836-43c0-8734-b8d1e73e0543',
'5a5d48da-c540-4fbf-a4f1-f198e5a98af6', '4aea5f1d-8269-4413-97ef-af95873353d6', '11c0668f-71f6-45b9-88c6-06471415631b')
ORDER BY price;
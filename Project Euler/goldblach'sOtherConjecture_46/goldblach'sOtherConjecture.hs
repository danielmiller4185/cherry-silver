--problem specification: It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.
--It turns out that the conjecture was false.
--What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?
--these functions solve the problem

isOddComposite x
    | x /= 1 && odd x && not (isPrime x) = True
    | otherwise = False

isPrime n = n > 1 && (all (\k -> gcd n k == 1)  (takeWhile (\k -> k*k <= n) [2..]))
    
listPrimes = filter (isPrime) [1..]

satisfiesConjecture :: Int -> Bool
satisfiesConjecture x = if (length list) > 0 then True else False
                            where list = [ (prime,twiceSquare) | prime <- takeWhile (<x) listPrimes, twiceSquare <- twiceSquares, prime + twiceSquare == x]
                                  list2 = [1..floor (sqrt (fromIntegral x))]
                                  twiceSquares = map (2*) (map (^2) list2)

--returns this first odd composite that doesn't satisfy the conjecture
findFirst :: [Int] -> Int
findFirst [] = 0
findFirst (x:xs)
    | satisfiesConjecture x = findFirst xs
    | otherwise = x

answer = findFirst (filter (isOddComposite) [1..])
--answer evaluates to 5777

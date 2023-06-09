import React, {useState} from 'react';
import Swiper from 'react-native-swiper';
import {useSelector} from 'react-redux';
import type {AppState, User} from '../redux';

import {useAxiosWithRefreshToken} from '../hooks/useAxioswithRfToken';

import {Common} from '../components/Common';
import {
  Container,
  Heading,
  MoneyWrapper,
  BalanceHeading,
  HideText,
  BtnWrapper,
  BalanceBtn,
  BalanceText,
  BtnText,
  AdImage,
  AdContainer,
  EventList,
  SingleEventContainer,
  EventText,
} from '../styles/HomeStyle';
import {SwiperConatiner, SwiperView} from '../styles/LoginStyle';

import ad1 from '../assets/ad_1.png';
import ad2 from '../assets/ad_2.png';
import ad3 from '../assets/ad_3.png';

interface responseDataType {
  message: string;
  logs: savingListDataType[];
}

interface savingListDataType {
  logTime: string;
  emotion: string;
  amount: number;
  total: number;
}

const Home = ({navigation}: {navigation: any}) => {
  const [balanceView, setBalanceView] = useState(true);
  const [year, setYear] = useState(new Date().getFullYear());
  const [month, setMonth] = useState(new Date().getMonth());

  const user = useSelector<AppState, User | null>(state => state.loggedUser);

  const {data, error, refetch} = useAxiosWithRefreshToken<responseDataType>(
    `https://feelingfilling.store/api/log/${year}/${month + 1}`,
    'GET',
    null,
  );

  // 로딩중 화면 설정하는 함수
  // const inProgress = useSelector<AppState, boolean>(state => state.inProgress);

  // const handleProgress = () => {
  //   dispatch(toggleProgress(!inProgress));
  // };

  // console.log(data.logs);
  // console.log(data);
  // console.log(text, error);

  const priceConverter = (price: string) => {
    return price.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  };

  return (
    <Container>
      <Heading>나의 감정 적금</Heading>
      <MoneyWrapper>
        <BalanceHeading>
          {user && user.name}님의 {month + 1}월 적금
        </BalanceHeading>
        {balanceView ? (
          <BalanceText>
            {data && data.logs.length > 0
              ? priceConverter(data.logs[0].total + '')
              : '0'}
            원
          </BalanceText>
        ) : (
          <HideText>잔액 숨김 중</HideText>
        )}
        <BtnWrapper>
          <BalanceBtn onPress={() => navigation.navigate('Saving')}>
            <BtnText>적립 내역</BtnText>
          </BalanceBtn>
          <BalanceBtn onPress={() => setBalanceView(!balanceView)}>
            {balanceView ? (
              <BtnText>잔액 숨기기</BtnText>
            ) : (
              <BtnText>잔액 보이기</BtnText>
            )}
          </BalanceBtn>
        </BtnWrapper>
      </MoneyWrapper>
      <Heading>진행 중 이벤트</Heading>
      <EventList>
        <SingleEventContainer>
          <EventText>5월 감정왕 이벤트 안내</EventText>
        </SingleEventContainer>
        <SingleEventContainer>
          <EventText>적금 환급 관련 안내</EventText>
        </SingleEventContainer>
      </EventList>
      <SwiperConatiner>
        <Swiper
          showsButtons={false}
          autoplay
          loop
          autoplayTimeout={3}
          activeDotColor={Common.colors.selectGrey}>
          <SwiperView>
            <AdContainer>
              <AdImage source={ad1} />
            </AdContainer>
          </SwiperView>
          <SwiperView>
            <AdContainer>
              <AdImage source={ad2} />
            </AdContainer>
          </SwiperView>
          <SwiperView>
            <AdContainer>
              <AdImage source={ad3} />
            </AdContainer>
          </SwiperView>
        </Swiper>
      </SwiperConatiner>
    </Container>
  );
};

export default Home;

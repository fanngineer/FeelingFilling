import styled from 'styled-components/native';
import {Common} from '../components/Common';

export const Container = styled.View`
  flex: 1;
  flex-direction: column;
  background-color: ${Common.colors.white01};
  justify-content: space-evenly;
  align-items: center;
  padding: 10px;
`;

export const FontLogo = styled.Image`
  width: 200px;
  height: 200px;
`;

export const SwiperConatiner = styled.View`
  height: 220px;
`;

export const SwiperView = styled.View`
  height: 150px;
  justify-content: center;
  text-align: center;
`;

export const SwiperText = styled.Text`
  font-family: 'NotoSansKR-Medium';
  text-align: center;
`;

export const LottieContainer = styled.View`
  height: 120px;
  margin-bottom: 10px;
`;

export const LoginBtn = styled.TouchableOpacity`
  flex-direction: row;
  background-color: ${Common.colors.kakao};
  padding: 10px 30px;
  border-radius: 30px;
  align-items: center;
`;

export const ColorBtn = styled.TouchableOpacity<{color: string}>`
  flex-direction: row;
  width: 200px;
  background-color: ${(props: any) => props.color};
  padding: 10px 30px;
  border-radius: 30px;
  align-items: center;
  justify-content: center;
`;

export const KakaoLogo = styled.Image`
  width: 40px;
  height: 40px;
  margin-right: 10px;
`;

export const BtnText = styled.Text<{textColor: string}>`
  font-family: 'NotoSansKR-Bold';
  color: ${(props: any) => props.textColor};
`;
